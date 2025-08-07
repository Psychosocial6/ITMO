package server.comands;

import java.util.LinkedHashMap;
import java.util.Map;
/**
 * Класс, реализующий команду вывода справки по доступным командам.
 * Реализует интерфейс {@link CommandWithoutArg}, что означает, что команда не принимает аргументов.
 * 
 */
public class CommandHelp extends Command<Void> {
	@Override 
    public Boolean checkUser(String login, String password){
		return null;
    }
	/**
     * Карта для хранения описаний команд.
     * Ключ — имя команды, значение — описание команды.
     */
	private final Map<String, String> commands = new LinkedHashMap<>();
	
	/**
     * Конструктор класса.
     * Инициализирует карту команд и их описаний.
     */
	public CommandHelp() {
		commands.put("help", "вывести справку по доступным командам");
        commands.put("info", "вывести информацию о коллекции");
        commands.put("show", "вывести все элементы коллекции");
        commands.put("add {element}", "добавить новый элемент в коллекцию");
        commands.put("update id {element}", "обновить элемент коллекции по id");
        commands.put("remove_by_id id", "удалить элемент по id");
        commands.put("clear", "очистить коллекцию");
        commands.put("save", "сохранить коллекцию в файл");
        commands.put("execute_script file_name", "исполнить команды из файла");
        commands.put("exit", "завершить программу");
        commands.put("add_if_max {element}", "добавить элемент, если он больше максимального");
        commands.put("add_if_min {element}", "добавить элемент, если он меньше минимального");
        commands.put("history", "вывести последние 15 команд");
        commands.put("min_by_genre", "вывести элемент с минимальным genre");
        commands.put("print_ascending", "вывести элементы в порядке возрастания");
        commands.put("print_field_descending_operator", "вывести поле operator в порядке убывания");
	}
	/**
     * Метод для выполнения команды вывода справки.
     * Выводит список всех доступных команд и их описаний.
     * 
     */
	@Override
	public String command(Void arg, String login, String password) {
		String output ="";
		for(Map.Entry<String, String> entry: commands.entrySet()) {
			output += entry.getKey() + "  --  " + entry.getValue() +'\n';
		}
		
		
		return output;

	}

}
