package server.comands;

import java.time.Instant;

import server.dataStorage.MovieCollection;

public abstract class Command<T> {
	 abstract String command( T arg, String login, String password);
	 abstract Boolean checkUser(String login, String password);
	 
	 public void setTimeLastUpdate() {
		 MovieCollection.setLastUpdate(Instant.now());
	 }
	 
}

