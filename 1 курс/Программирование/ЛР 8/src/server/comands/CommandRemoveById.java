package server.comands;

import java.sql.SQLException;

import server.Server;
import server.dataStorage.Movie;
import server.dataStorage.MovieCollection;

/**
 * Класс {@code CommandRemoveById} реализует команду удаления фильма из коллекции по его ID.
 */
public class CommandRemoveById extends Command<Long> {
	Movie movieToRemove;
	@Override 
    public Boolean checkUser(String login, String password){
		return (login.equals(movieToRemove.getCreatedBy()) && Server.allUsers.authenticate(login, password));
    }
    /**
     * Удаляет фильм с заданным ID из коллекции.
     *
     * @param id ID фильма, который нужно удалить.
     */
    @Override
    public String command(Long id, String login, String password) {
    	movieToRemove = MovieCollection.movies.stream()
    	        .filter(movie -> movie.getId() == id)
    	        .findFirst()
    	        .orElse(null);
    	
    	if (movieToRemove == null) {
    		return "Фильма с таким id нет в коллекции ";
    	}
    	
    	if(!checkUser(login, password)) {
    		return "У вас нет прав для удаления этого фильма ";
    	}
    	
       try{
    	   Server.movieManager.deleteMovie(id);
    	   MovieCollection.movies.remove(movieToRemove);
      		setTimeLastUpdate();
    	   return "Фильм успешно удалён ";
       }
       catch(SQLException e) {
    	   return "Ошибка при удалении фильма: "+ e.getMessage();
       }
    }
}