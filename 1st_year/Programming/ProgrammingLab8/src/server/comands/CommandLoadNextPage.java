package server.comands;

import java.sql.SQLException;
import java.util.List;

import server.Server;

public class CommandLoadNextPage extends Command<Long> {


	@Override
	Boolean checkUser(String login, String password) {
		return null;
	}

	
	@Override
	String command(Long arg, String login, String password) {
		
		try {
			List<String> movies = Server.movieManager.loadNextPage(arg);
			return "NextPage "+ String.join("\n", movies);
		} catch (SQLException e) {
			return e.getMessage();
		}
		
		
		
	}
	
	
	
}
