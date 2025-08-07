package server.comands;

import java.sql.SQLException;
import java.util.List;

import server.Server;

public class CommandLoadNextSortedPage extends Command<Object[]>{

	@Override
	String command(Object[] arg, String login, String password) {
		try{
			Long page = (Long) arg[0];
			String sortingParametr = (String) arg[1];
			try {
				List<String> movies = Server.movieManager.loadNextSortedPage(page, sortingParametr);
				return "NextPage " + String.join("\n", movies);
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
		// TODO Auto-generated method stub
		return null;
	}

}
