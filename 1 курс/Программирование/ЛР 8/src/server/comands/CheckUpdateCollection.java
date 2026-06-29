package server.comands;

import java.time.Instant;


import server.dataStorage.MovieCollection;

public class CheckUpdateCollection extends Command<Void> {
	private CommandLoadNextPage loadNextPage;

	//добавить реализацию текущей страницы
	@Override
	String command(Void arg, String login, String password) {
		if(MovieCollection.getLastUpdate().isAfter(Instant.now().minusSeconds(3))) {
			return "Обновленияесть";
		}
		else return "ОбновленийНеБыло";
	}

	@Override
	Boolean checkUser(String login, String password) {
		return null;
	}

}
