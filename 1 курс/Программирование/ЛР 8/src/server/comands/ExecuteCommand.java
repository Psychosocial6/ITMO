package server.comands;

import java.util.Map;

import server.dataStorage.CommandRegistry;

public class ExecuteCommand {
	static CommandRegistry commandRegistry = new CommandRegistry();
	static Map<String, Command<?>> commands = commandRegistry.getCommands();
    @SuppressWarnings("unchecked")
    public static String executeCommand(String commandName,  Object[] args, String login, String password) {
        Command<?> command = commands.get(commandName);

        if (command == null) {
            return "Ошибка: Команда не найдена.";
        }

        try { 
            if (args.length == 2 && args[0] instanceof Long && args[1] instanceof Map || args.length == 2 && args[0] instanceof Number && args[1] instanceof String) { 
                Command<Object[]> typedCommand = (Command<Object[]>) command;
                return typedCommand.command(args, login, password);
            } else if (args.length == 1 && args[0] instanceof Long) { 
                Command<Long> typedCommand = (Command<Long>) command;
                return typedCommand.command((Long) args[0], login, password);
            } else if (args.length == 1 && args[0] instanceof Map) { 
                Command<Map<String, Object>> typedCommand = (Command<Map<String, Object>>) command;
                return typedCommand.command((Map<String, Object>) args[0], login, password);
            } else if (args.length == 0) { 
                Command<Void> typedCommand = (Command<Void>) command;
                return typedCommand.command(null, login, password); 
            } else {
                return "Ошибка: Неверный формат аргументов для команды.";
            }
        } catch (ClassCastException e) {
            return "Ошибка приведения типов: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ошибка выполнения команды: " + e.getMessage();
        }
    }
}


