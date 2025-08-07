package commands;

import managers.CollectionManager;

import java.util.ArrayList;

/**
 * Класс команды помощи
 * @author Андрей
 * */
public class CommandHelp extends Command{

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandHelp(CollectionManager collectionManager) {
        super(collectionManager, 0);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public void execute(Object[] args) {
        for (Object command: args) {
            System.out.println(((Command) command).getInfo());
        }
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "help: вывести справку по доступным командам";
    }
}
