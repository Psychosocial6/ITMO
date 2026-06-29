package server.comands;

import java.sql.SQLException;
import java.util.Iterator;

import server.Server;
import server.dataStorage.Movie;
import server.dataStorage.MovieCollection;

/**
 * Класс, реализующий команду очистки коллекции фильмов.
 * Реализует интерфейс {@link CommandWithoutArg}, что означает, что команда не принимает аргументов.
 * 
 */
public class CommandClear extends Command<Void> {
	public Boolean checkUser(String login, String password){
		return (Server.allUsers.authenticate(login, password));
    }
	/**
     * Метод для выполнения команды очистки коллекции.
     * Удаляет все элементы из коллекции {@link MovieCollection#movies}.
	 * @return 
     */
	
	@Override
	public String command(Void arg, String login, String password) {
		Iterator<Movie> iterator = MovieCollection.movies.iterator();
	    while (iterator.hasNext()) {
	        Movie movieToClear = iterator.next();
	        if (movieToClear.getCreatedBy().equals(login) && checkUser(login, password)) {
	            try {
	                Server.movieManager.deleteMovie(movieToClear.getId());
	                iterator.remove(); 
	            } catch (SQLException e) {
	                return "Ошибка при удалении из БД: " + e.getMessage();
	            }
	        }
	    }
   		setTimeLastUpdate();
	    return "Коллекция очищена";
	}

}
