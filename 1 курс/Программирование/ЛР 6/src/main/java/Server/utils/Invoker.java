package Server.utils;

import Server.commands.*;
import Server.exceptions.ExecutionException;
import Server.managers.CollectionManager;

import java.util.HashMap;

/**
 * Класс хранящий команды и осуществляющий их исполнение
 * @author Андрей
 */
public class Invoker {

    private HashMap<String, Command> commands = new HashMap<>();


    /**
     * Конструктор
     * @param collectionManager - управляемая коллекция
     */
    public Invoker(CollectionManager collectionManager) {
        commands.put("help", new CommandHelp(collectionManager, this));
        commands.put("info", new CommandInfo(collectionManager));
        commands.put("show", new CommandShow(collectionManager));
        commands.put("insert", new CommandInsert(collectionManager));
        commands.put("update", new CommandUpdate(collectionManager));
        commands.put("remove_key", new CommandRemoveKey(collectionManager));
        commands.put("clear", new CommandClear(collectionManager));
        commands.put("save", new CommandSave(collectionManager));
        commands.put("remove_lower", new CommandRemoveLower(collectionManager));
        commands.put("replace_if_greater", new CommandReplaceIfGreater(collectionManager));
        commands.put("replace_if_lower", new CommandReplaceIfLower(collectionManager));
        commands.put("filter_contains_name", new CommandFilterContainsName(collectionManager));
        commands.put("filter_starts_with_name", new CommandFilterStartsWithName(collectionManager));
        commands.put("print_field_ascending_distance_travelled", new CommandPrintFieldAscendingDistanceTravelled(collectionManager));
        commands.put("execute_script", new CommandExecute(collectionManager, this));
    }

    /**
     * Метод возвращающий все команды
     * @return Server.commands - словарь <ключ, команда>
     */
    public HashMap<String, Command> getCommands() {
        return commands;
    }


    /**
     * Метод исполняющий команду по ее строковому названию
     * @param token - ключ под которым хранится команда
     * @param args - аргументы команды
     * @throws ExecutionException - исключение при ошибке исполнения команды
     */
    public String executeCommandUsingToken(String token, Object[] args) throws ExecutionException {
        try {
            return commands.get(token).execute(args);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getClass());
            e.printStackTrace();
            throw new ExecutionException(String.format("Command %s's execution failed", token));
        }
    }
}