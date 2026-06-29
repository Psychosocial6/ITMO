package server.comands;

import java.sql.SQLException;
import java.util.List;

import server.Server;

public class CommandLoadNextFilteredPage extends Command<Object[]> {

	@Override
	String command(Object[] arg, String login, String password) {
		try{
			Long page = (Long) arg[0];
			String filterParams = (String) arg[1];
			String[] parts = filterParams.trim().split(" ", 2); 
			
			String filterParametrs = parts[0];
			String filterValue = parts[1];
			try {
				List<String> movies =Server.movieManager.loadNextFilteredPage(page, filterParametrs, filterValue); 
				return "NextPage "+ String.join("\n", movies);
			}
			catch(SQLException e) {
				return e.getMessage();
			}
			
		}
		catch (ClassCastException | ArrayIndexOutOfBoundsException e) {
            return "Ошибка приведения типов: " + e.getMessage();
		}

	}

	@Override
	Boolean checkUser(String login, String password) {
		return null;
	}

}
