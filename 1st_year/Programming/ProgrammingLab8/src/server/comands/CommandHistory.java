package server.comands;

/**
 * Класс, реализующий команду вывода истории выполненных команд.
 * Реализует интерфейс {@link CommandWithoutArg}, что означает, что команда не принимает аргументов.
 * Хранит последние 15 выполненных команд.
 * 
 */
public class CommandHistory extends Command<Void> {
	@Override 
    public Boolean checkUser(String login, String password){
		return null;
    }
	/**
     * Массив для хранения последних 15 выполненных команд.
     */
	private static String[] commandHistory = new String[15];
	
	/**
     * Метод для добавления команды в историю.
     * Если история заполнена, удаляет самую старую команду и добавляет новую.
     * 
     * @param command Команда, которую нужно добавить в историю.
     */
	public void addToHistory(String command) {
		boolean hit = false;
	
		if(commandHistory[14] == null) {
			for (int i= 0; i<15; i++) {
				if(commandHistory[i]==null && hit ==false) {
					commandHistory[i] = command;
					hit = true;
				}
			}
		}
		else {
			for(int i= 0; i<14; i++) {
				commandHistory[i] = commandHistory[i+1];	
			}
			commandHistory[14]= command;
			hit = true;
		}
	}
	/**
     * Метод для выполнения команды вывода истории.
     * Выводит список последних выполненных команд.
	 * @return 
     */
	@Override
	public String command( Void arg, String login, String password) {
		String output = "";
		System.out.println("последние команды:");
		for(String command: commandHistory) {
			if(command!= null) {
				 output += "- "+command +'\n';
			}
		}
		return output;
		
	}
	
	
}
