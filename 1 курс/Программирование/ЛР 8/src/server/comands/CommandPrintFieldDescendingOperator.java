package server.comands;

import server.dataStorage.Movie;
import server.dataStorage.MovieCollection;
import server.dataStorage.Person;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Класс {@code CommandPrintFieldDescendingOperator} реализует команду вывода всех операторов
 * фильмов в порядке убывания по их имени.
 */
public class CommandPrintFieldDescendingOperator extends Command<Void> {
	@Override 
    public Boolean checkUser(String login, String password){
		return null;
    }
    /**
     * Выполняет команду сортировки и вывода операторов фильмов в порядке убывания.
     * Если у фильма нет оператора (поле {@code operator} равно {@code null}),
     * он не включается в список.
     */
    @Override
    public String command(Void arg, String login, String password) {
        return "Вывод всех операторов в порядке убывания:\n" +
                MovieCollection.movies.stream()
                        .map(Movie::getOperator)
                        .filter(operator -> operator != null)
                        .sorted(Comparator.comparing(Person::getName).reversed())
                        .map(Person::toString)
                        .collect(Collectors.joining("\n")) + "\n";
    }
}
