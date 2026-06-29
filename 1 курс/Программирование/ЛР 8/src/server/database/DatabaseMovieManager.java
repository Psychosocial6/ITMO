package server.database;

import server.dataStorage.*;

import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class DatabaseMovieManager {
    private final Connection connection;

    public DatabaseMovieManager(Connection connection) {
        this.connection = connection;
    }

    public void addMovieToDatabase(Movie movie) throws SQLException {
        // Вставка координат
        String insertCoordinatesSQL = "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id";
        long coordinatesId;
        try (PreparedStatement ps = connection.prepareStatement(insertCoordinatesSQL)) {
            ps.setLong(1, movie.getCoordinates().getX());
            ps.setDouble(2, movie.getCoordinates().getY());
            ResultSet rs = ps.executeQuery();
            rs.next();
            coordinatesId = rs.getLong(1);
        }

        // Вставка локации
        String insertLocationSQL = "INSERT INTO location (x, y, z, name) VALUES (?, ?, ?, ?) RETURNING id";
        long locationId = 0;
        if (movie.getOperator() != null && movie.getOperator().getLocation() != null) {
            try (PreparedStatement ps = connection.prepareStatement(insertLocationSQL)) {
                ps.setInt(1, movie.getOperator().getLocation().getX());
                ps.setDouble(2, movie.getOperator().getLocation().getY());
                ps.setFloat(3, movie.getOperator().getLocation().getZ());
                ps.setString(4, movie.getOperator().getLocation().getName());
                ResultSet rs = ps.executeQuery();
                rs.next();
                locationId = rs.getLong(1);
            }
        }

        // Вставка оператора
        String insertPersonSQL = "INSERT INTO person (name, height, eye_color, hair_color, nationality, location_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        long operatorId = 0;
        if (movie.getOperator() != null) {
            try (PreparedStatement ps = connection.prepareStatement(insertPersonSQL)) {
                ps.setString(1, movie.getOperator().getName());
                ps.setDouble(2, movie.getOperator().getHeight());
                ps.setString(3, movie.getOperator().getEyeColor().toString());
                ps.setString(4, movie.getOperator().getHairColor().toString());
                ps.setString(5, movie.getOperator().getNationality().toString());
                ps.setObject(6, movie.getOperator().getLocation() != null ? locationId : null);
                ResultSet rs = ps.executeQuery();
                rs.next();
                operatorId = rs.getLong(1);
            }
        }

        // Вставка фильма
        String insertMovieSQL = """
            INSERT INTO movie (name, coordinates_id, creation_date, oscars_count, total_box_office, usa_box_office, genre, operator_id, created_by)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (PreparedStatement ps = connection.prepareStatement(insertMovieSQL)) {
            ps.setString(1, movie.getName());
            ps.setLong(2, coordinatesId);
            ps.setTimestamp(3, Timestamp.from(movie.getCreationDate().toInstant()));
            ps.setInt(4, movie.getOscarsCount());
            ps.setDouble(5, movie.getTotalBoxOffice());
            ps.setObject(6, movie.getUsaBoxOffice());
            ps.setString(7, movie.getGenre() != null ? movie.getGenre().toString() : null);
            ps.setObject(8, operatorId != 0 ? operatorId : null);
            ps.setString(9, movie.getCreatedBy());
            ps.executeUpdate();
        }
        
    }

    
    public HashSet<Movie> loadAllMovies() throws SQLException {
        HashSet<Movie> movies = new HashSet<>();
        String query = """
            SELECT m.id, m.name, m.creation_date, m.oscars_count, m.total_box_office, m.usa_box_office, m.genre, m.created_by,
                   c.x AS coord_x, c.y AS coord_y,
                   p.id AS person_id, p.name AS person_name, p.height, p.eye_color, p.hair_color, p.nationality,
                   l.x AS loc_x, l.y AS loc_y, l.z AS loc_z, l.name AS loc_name
            FROM movie m
            JOIN coordinates c ON m.coordinates_id = c.id
            LEFT JOIN person p ON m.operator_id = p.id
            LEFT JOIN location l ON p.location_id = l.id
            """;
        try (Statement stmt = connection.createStatement(); 
        	ResultSet rs = stmt.executeQuery(query)) 
        	{
            while (rs.next()) {
                Coordinates coords = new Coordinates(rs.getInt("coord_x"), rs.getLong("coord_y"));
                ZonedDateTime creationDate = rs.getObject("creation_date", Timestamp.class)
                                               .toInstant().atZone(ZoneId.systemDefault());

                Person operator = null;
                if (rs.getString("person_name") != null) {
                    Location location = null;
                    if (rs.getString("loc_name") != null) {
                        location = new Location(
                            rs.getInt("loc_x"),
                            rs.getLong("loc_y"),
                            rs.getInt("loc_z"),
                            rs.getString("loc_name")
                        );
                    }

                    operator = new Person(
                        rs.getString("person_name"),
                        rs.getInt("height"),
                        Color.valueOf(rs.getString("eye_color")),
                        Color.valueOf(rs.getString("hair_color")),
                        Country.valueOf(rs.getString("nationality")),
                        location
                    );
                }

                Movie movie = new Movie(
                    rs.getLong("id"),
                    rs.getString("name"),
                    coords,
                    creationDate,
                    rs.getInt("oscars_count"),
                    rs.getDouble("total_box_office"),
                    rs.getDouble("usa_box_office"),
                    rs.getString("genre") != null ? MovieGenre.valueOf(rs.getString("genre")) : null,
                    operator,
                    rs.getString("created_by")
                );
                movie.setCreatedBy(rs.getString("created_by"));
                movies.add(movie);
            }
        }

        return movies;
    }
    
    public void deleteMovie(Long movieId) throws SQLException {
    	connection.setAutoCommit(false); // начать транзакцию

        try (
            PreparedStatement getRefsStmt = connection.prepareStatement("""
                SELECT operator_id, coordinates_id
                FROM movie
                WHERE id = ?
            """);

            PreparedStatement getLocationStmt = connection.prepareStatement("""
                SELECT location_id FROM person WHERE id = ?
            """);

            PreparedStatement deleteMovieStmt = connection.prepareStatement("""
                DELETE FROM movie WHERE id = ?
            """);

            PreparedStatement deleteLocationStmt = connection.prepareStatement("""
                DELETE FROM location WHERE id = ?
            """)
        ) {
            Integer operatorId = null;
            Integer coordinatesId = null;
            Integer locationId = null;

            getRefsStmt.setLong(1, movieId);
            try (ResultSet rs = getRefsStmt.executeQuery()) {
                if (rs.next()) {
                    operatorId = rs.getInt("operator_id");
                    coordinatesId = rs.getInt("coordinates_id");
                }
            }

            if (operatorId != null) {
                getLocationStmt.setInt(1, operatorId);
                try (ResultSet rs = getLocationStmt.executeQuery()) {
                    if (rs.next()) {
                        locationId = rs.getInt("location_id");
                    }
                }
            }

            deleteMovieStmt.setLong(1, movieId);
            deleteMovieStmt.executeUpdate();

            if (locationId != null) {
                deleteLocationStmt.setInt(1, locationId);
                deleteLocationStmt.executeUpdate();
            }

            connection.commit(); 
        } catch (SQLException e) {
            connection.rollback(); 
            throw e;
        } finally {
            connection.setAutoCommit(true); 
        }
    }
    
    public void updateMovie(Long id, Movie movie) throws SQLException {
        // Обновление movie, coordinates, person, location 
    	String updateMovieQuery = """
    		    UPDATE movie
    		    SET name = ?, oscars_count = ?, total_box_office = ?, usa_box_office = ?, genre = ?
    		    WHERE id = ?;
    		    """;

    	String updateCoordinatesQuery = """
    		    UPDATE coordinates
    		    SET x = ?, y = ?
    		    WHERE id = (
    		        SELECT coordinates_id FROM movie WHERE id = ?
    		    );
    		    """;

    	String updatePersonQuery = """
    		    UPDATE person
    		    SET name = ?, height = ?, eye_color = ?, hair_color = ?, nationality = ?
    		    WHERE id = (
    		        SELECT operator_id FROM movie WHERE id = ?
    		    );
    		    """;

    	String updateLocationQuery = """
    		    UPDATE location
    		    SET x = ?, y = ?, z = ?, name = ?
    		    WHERE id = (
    		        SELECT location_id FROM person WHERE id = (
    		            SELECT operator_id FROM movie WHERE id = ?
    		        )
    		    );
    		    """;
    	
        try (PreparedStatement movieStmt = connection.prepareStatement(updateMovieQuery);
             PreparedStatement coordStmt = connection.prepareStatement(updateCoordinatesQuery);
             PreparedStatement personStmt = connection.prepareStatement(updatePersonQuery);
             PreparedStatement locationStmt = connection.prepareStatement(updateLocationQuery)) {

            // movie
            movieStmt.setString(1, movie.getName());
            movieStmt.setInt(2, movie.getOscarsCount());
            movieStmt.setDouble(3, movie.getTotalBoxOffice());
            if (movie.getUsaBoxOffice() != null) {
                movieStmt.setDouble(4, movie.getUsaBoxOffice());
            } else {
                movieStmt.setNull(4, java.sql.Types.DOUBLE);
            }
            if (movie.getGenre() != null) {
                movieStmt.setString(5, movie.getGenre().toString());
            } else {
                movieStmt.setNull(5, java.sql.Types.VARCHAR);
            }
            movieStmt.setLong(6, id);
            movieStmt.executeUpdate();

            // coordinates
            coordStmt.setInt(1, movie.getCoordinates().getX());
            coordStmt.setLong(2, movie.getCoordinates().getY());
            coordStmt.setLong(3, id);
            coordStmt.executeUpdate();

            // person
            Person person = movie.getOperator();
            personStmt.setString(1, person.getName());
            personStmt.setInt(2, person.getHeight());
            if (person.getEyeColor() != null)
                personStmt.setString(3, person.getEyeColor().toString());
            else
                personStmt.setNull(3, java.sql.Types.VARCHAR);
            
            if (person.getHairColor() != null)
                personStmt.setString(4, person.getHairColor().toString());
            else
                personStmt.setNull(4, java.sql.Types.VARCHAR);
            if (person.getNationality() != null)
                personStmt.setString(5, person.getNationality().toString());
            else
                personStmt.setNull(5, java.sql.Types.VARCHAR);
            personStmt.setLong(6, id);
            personStmt.executeUpdate();

            // location
            Location loc = person.getLocation();
            locationStmt.setFloat(1, loc.getX());
            locationStmt.setDouble(2, loc.getY());
            locationStmt.setFloat(3, loc.getZ());
            if (loc.getName() != null)
                locationStmt.setString(4, loc.getName());
            else
                locationStmt.setNull(4, java.sql.Types.VARCHAR);
            locationStmt.setLong(5, id);
            locationStmt.executeUpdate();
        }
    }
    
    
    public ArrayList<String> loadNextPage(Long arg) throws SQLException{ 
    	ArrayList<String> moviePage = new ArrayList<>();
    	String query = """
    			SELECT movie.id, movie.name, coordinates.x, coordinates.y, movie.created_by FROM movie 
    			JOIN coordinates ON coordinates.id = movie.coordinates_id
    			ORDER BY id LIMIT ? OFFSET ?
    			""";
    	try(PreparedStatement stmt = connection.prepareStatement(query)){
    		stmt.setLong(1, 50);
    		stmt.setLong(2, (arg-1)*50);
    		ResultSet rs = stmt.executeQuery();
    		 while (rs.next()) {
    	            Long id = rs.getLong("id");
    	            String name = rs.getString("name");
    	            Integer x =rs.getInt("x");
    	            Long y =rs.getLong("y");
    	            String createdBy = rs.getString("created_by");
     	            moviePage.add(id + " " + name + " " + x.toString() + " " + y.toString() +" "+ createdBy);
    	    }
    		return moviePage;
    	}
    }
    
    public ArrayList<String> loadNextSortedPage(Long page, String sortingParametr) throws SQLException{ 
    	ArrayList<String> moviePage = new ArrayList<>();
    	
    	List<String> allowedSortFields = List.of("movie.name", "movie.id", "movie.oscars_count", "movie.total_box_office", "movie.usa_box_office", "movie.genre", "movie.operator_id", "movie.created_by", "coordinates.x", "coordinates_id", "coordinates.y", "operator_id", "person.name", "person.height", "person.eye_color", "person.hair_color", "person.nationality", "person.location_id", "location.x", "location.y", "location.z", "location.name" );
        if (!allowedSortFields.contains(sortingParametr.toLowerCase().trim())) {
            throw new IllegalArgumentException("Недопустимый параметр сортировки: " + sortingParametr);
        }
    	String query = """
    			SELECT movie.id, movie.name, coordinates.x, coordinates.y, movie.created_by FROM movie 
    			JOIN coordinates ON coordinates.id = movie.coordinates_id
    			JOIN person ON person.id = movie.operator_id
    			JOIN location ON location.id = person.location_id
    			ORDER BY %s 
    			LIMIT ? OFFSET ?
    			""".formatted(sortingParametr.trim());
    	try(PreparedStatement stmt = connection.prepareStatement(query)){
    		stmt.setLong(1, 50);
    		stmt.setLong(2, (page-1)*50);
    		ResultSet rs = stmt.executeQuery();
    		while (rs.next()) {
    			 Long id = rs.getLong("id");
 	            String name = rs.getString("name");
 	            Integer x =rs.getInt("x");
 	            Long y =rs.getLong("y");
 	           String createdBy = rs.getString("created_by");
 	            moviePage.add(id + " " + name + " " + x.toString() + " " + y.toString() + " "+ createdBy);
    		}
    		return moviePage;
    	}
    }
    
    public ArrayList<String> loadNextFilteredPage(Long page, String filterParametr, String filterValue) throws SQLException{ 
    	ArrayList<String> moviePage = new ArrayList<>();
    	String[] filterParams =  filterValue.split("\\s+");
    	
    	List<String> allowedSortFields = List.of("movie.name", "movie.id", "movie.oscars_count", "movie.total_box_office", "movie.usa_box_office", "movie.genre", "movie.operator_id", "movie.created_by", "movie.coordinates.x", "movie.coordinates_id", "coordinates.y", "operator_id", "person.name", "person.height", "person.eye_color", "person.hair_color", "person.nationality", "person.location_id", "location.x", "location.y", "location.z", "location.name" );
    	
    	List<String> allowedOperators = List.of("=", ">", "<", ">=", "<=", "<>");
    	 
        if (!allowedSortFields.contains(filterParametr.toLowerCase()) || filterParams.length != 2 || !allowedOperators.contains(filterParams[0])) {
            throw new IllegalArgumentException("Недопустимые параметры сортировки");
        }
    	String query = """
    			SELECT movie.id, movie.name, coordinates.x, coordinates.y, movie.created_by  FROM movie 
    			JOIN coordinates ON coordinates.id = movie.coordinates_id
    			JOIN person ON person.id = movie.operator_id
    			JOIN location ON location.id = person.location_id
    			WHERE %s %s ? 
    			LIMIT ? OFFSET ?
    			""".formatted(filterParametr, filterParams[0]);
    	try(PreparedStatement stmt = connection.prepareStatement(query)){
    		stmt.setObject(1, filterParams[1]);
    		stmt.setInt(2, 50);
    		stmt.setLong(3, (page-1)*50);
    		ResultSet rs = stmt.executeQuery();
    		while (rs.next()) {
    			Long id = rs.getLong("id");
 	            String name = rs.getString("name");
 	            Integer x =rs.getInt("x");
 	            Long y =rs.getLong("y");
 	            String createdBy = rs.getString("created_by");
 	            moviePage.add(id + " " + name + " " + x.toString() + " " + y.toString()+ " "+ createdBy);
    		}
    		return moviePage;
    	}
    }
    
    public String showMovie(Long id) throws SQLException {
        String query = """
            SELECT movie.*, coordinates.*, person.*, location.*
            FROM movie
            JOIN coordinates ON coordinates.id = movie.coordinates_id
            JOIN person ON person.id = movie.operator_id
            JOIN location ON location.id = person.location_id
            WHERE movie.id = ?
            """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    StringBuilder sb = new StringBuilder();
                    ResultSetMetaData metaData = rs.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        Object value = rs.getObject(i);
                        sb.append(value != null ? value.toString() : "null");
                        sb.append("\n");
                    }

                    return "ThisMovie" +"\n" + sb.toString();
                } else {
                    return "Ошибка: такой фильм не найден (*_*)"; 
                }
            }
        }
    }
}




