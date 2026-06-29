package Server.commands;

import Server.managers.CollectionManager;
import Server.utils.Invoker;

/**
 * Класс команды исполнения скрипта
 * @author Андрей
 * */
public class CommandExecute extends Command {
    private Invoker invoker;

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * @param invoker - класс управляющий командами
     * */
    public CommandExecute(CollectionManager collectionManager, Invoker invoker) {
        super(collectionManager);
        this.invoker = invoker;
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        String file = (String) args[0];
        return collectionManager.executeScript(file, invoker);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "execute_script file_name: считать и исполнить скрипт из указанного файла";
    }
}