package server.comands;

import server.Server;

import java.sql.SQLException;


/**
 * Класс {@code CommandShow} реализует команду вывода всех элементов коллекции.
 */
public class CommandShow extends Command<Long> {
	@Override 
    public Boolean checkUser(String login, String password){
		return null;
    }
    /**
     * Выводит строковое представление каждого фильма в коллекции.
     */
    @Override
    public String command(Long arg, String login, String password) {
        try {

			return Server.movieManager.showMovie(arg);
		} catch (SQLException e) {
			return e.getMessage();
		}
    }
}
