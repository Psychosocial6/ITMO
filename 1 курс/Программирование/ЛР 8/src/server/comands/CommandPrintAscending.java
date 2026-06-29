package server.comands;

import server.dataStorage.Movie;
import server.dataStorage.MovieCollection;

import java.util.stream.Collectors;

/**
 * Класс {@code CommandPrintAscending} реализует команду вывода фильмов
 * в порядке возрастания по их естественному порядку сортировки.
 */
public class CommandPrintAscending extends Command<Void> {
	@Override 
    public Boolean checkUser(String login, String password){
		return null;
    }
    /**
     * Выполняет команду сортировки и вывода фильмов в порядке возрастания.
     * Сортировка основана на естественном порядке объектов {@code Movie},
     * что предполагает реализацию интерфейса {@code Comparable<Movie>}.
     */
    @Override
    public String command(Void arg,String login, String password) {
        return "Вывод фильмов по возрастанию сборов:\n" +
                MovieCollection.movies.stream()
                        .sorted()
                        .map(Movie::getName)
                        .collect(Collectors.joining("\n")) + "\n";
    }
}
