package server.comands;

import server.Server;
import server.dataStorage.*;
import client.dataValidation.*;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Map;

public class CommandUpdate extends Command<Object[]> {
	Movie movieToUpdate;
	
	@Override 
    public Boolean checkUser(String login, String password){
		return (!login.equals(movieToUpdate.getCreatedBy()) || Server.allUsers.authenticate(login, password));
    }

    @Override
    public String command(Object[] args, String login, String password) {    	 
        if (args.length != 2 || !(args[0] instanceof Number) || !(args[1] instanceof Map)) {
            return "Ошибка: Некорректный формат аргументов для команды.";
        }

        long id = ((Number) args[0]).longValue();
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) args[1];

        // Ищем фильм по ID
        movieToUpdate = MovieCollection.movies.stream()
            .filter(movie -> movie.getId() == id)
            .findFirst()
            .orElse(null);
        

        if (movieToUpdate == null) {
            return "Фильм с ID = " + id + " не найден.";
        }
        
        if(!checkUser(login, password)) {
        	return "У вас недостаточно прав для изменения фильма";
        }
        try {
        	
        	Coordinates coordinates = new Coordinates((int) data.get("Coordinates_X"), (long) data.get("Coordinates_Y"));
            Location location = new Location((Integer) data.get("Location_X"), (Long) data.get("Location_Y"), (int) data.get("Location_Z"), (String) data.get("Location_Name"));
            Person operator = new Person((String) data.get("Operator_Name"), (int) data.get("Operator_Height"), (Color) Color.valueOf((String) data.get("Operator_Eye")), (Color) Color.valueOf((String)data.get("Operator_Hair")), (Country) Country.valueOf((String) data.get("Operator_Nation")), location);
            Movie updatedMovie = new Movie(
            	movieToUpdate.getId(),
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

            // Проверяем корректность нового фильма
            if (!IsValidMovie.isValidMovie(updatedMovie)) {
                return "Ошибка: Введенные данные фильма некорректны.";
            }

            try {
            	Server.movieManager.updateMovie(id, updatedMovie);
            	MovieCollection.movies.remove(movieToUpdate);
            	MovieCollection.movies.add(updatedMovie);
           		setTimeLastUpdate();
            
            return "Фильм с ID = " + id + " успешно обновлён.";
            }
            catch(SQLException e) {
            	return "Ошибка обновлении в базе данных "+ e.getMessage();
            }
            
        } catch (Exception e) {
            return "Ошибка при обновлении данных фильма: " + e.getMessage();
        }
    }
}

