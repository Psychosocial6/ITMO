package server.comands;

import server.dataStorage.MovieCollection;

/**
 * Класс {@code CommandInfo} реализует команду вывода информации о коллекции.
 */
public class CommandInfo extends Command<Void> {
	@Override 
    public Boolean checkUser(String login, String password){
		return null;
    }
	/**
     * Выполняет команду вывода информации о коллекции.
     * Выводит дату инициализации, тип коллекции и количество элементов.
	 * @return 
     */
	@Override
	public String command(Void arg, String login, String password) {
		String output = "";
		output += "информация о коллекции:"+ '\n';
		output += "дата инициализации: " + MovieCollection.getInitializationdate() + '\n';
		output += "тип коллекции :" + MovieCollection.movies.getClass().getSimpleName()+ '\n';
		output += "колличество элементов :" + MovieCollection.movies.size() + '\n';
		return output;

	}

}
