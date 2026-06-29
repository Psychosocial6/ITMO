package server.comands;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Map;

import client.dataValidation.IsValidMovie;
import server.Server;
import server.dataStorage.Color;
import server.dataStorage.Coordinates;
import server.dataStorage.Country;
import server.dataStorage.Location;
import server.dataStorage.Movie;
import server.dataStorage.MovieCollection;
import server.dataStorage.MovieGenre;
import server.dataStorage.Person;

/**
 * Класс, реализующий команду добавления нового фильма в коллекцию, если его сборы являются максимальными.
 */
public class CommandAddIfMax extends Command<Map<String, Object>> {
	@Override 
    public Boolean checkUser(String login, String password){
		return null;
    }
	
    @Override
    public String command(Map<String, Object> data, String login, String password) {
        if (data == null) {
            return "Ошибка: данные о фильме отсутствуют.";
        }

        double maxTotalBoxOffice = MovieCollection.movies.stream()
                .mapToDouble(Movie::getTotalBoxOffice)
                .max()
                .orElse(0); 

        try {
            double totalBoxOffice = (double) data.get("TotalBoxOffice");

            if (totalBoxOffice <= maxTotalBoxOffice) {
                return "Ошибка: Сборы фильма не являются максимальными.";
            }

            Coordinates coordinates = new Coordinates((int) data.get("Coordinates_X"), (long) data.get("Coordinates_Y"));
            Location location = new Location((Integer) data.get("Location_X"), (Long) data.get("Location_Y"), (int) data.get("Location_Z"), (String) data.get("Location_Name"));
            Person operator = new Person((String) data.get("Operator_Name"), (int) data.get("Operator_Height"), (Color) Color.valueOf((String) data.get("Operator_Eye")), (Color) Color.valueOf((String)data.get("Operator_Hair")), (Country) Country.valueOf((String) data.get("Operator_Nation")), location);
            Movie movie = new Movie(
            	Server.idGeneration.getCurrentIdFromSequence()+1,
                (String) data.get("Name"),
                coordinates,
                ZonedDateTime.now(),
                (int) data.get("OscarsCount"),
                (double) data.get("TotalBoxOffice"),
                (double) data.get("UsaBoxOffice"),
                (MovieGenre) MovieGenre.valueOf((String) data.get("Genre")),
                operator,
                login
            );

            if (!IsValidMovie.isValidMovie(movie)) {
                return "Ошибка: В введённых данных обнаружена ошибка, фильм не добавлен.";
            }

            try {
        		Server.movieManager.addMovieToDatabase(movie);
        		MovieCollection.movies.add(movie);
           		setTimeLastUpdate();
        	}
        		catch(SQLException e){
        			return "Ошибка при добавлении в базу данных"+ e.getMessage();
        	}
            return "Фильм успешно добавлен в коллекцию.";

        } catch (NullPointerException | ClassCastException| SQLException e) {
            return "Ошибка: Некорректные данные о фильме.";
        }
    }
}

