package Server.commands;

import Server.managers.CollectionManager;

/**
 * Класс команды очистки коллекции
 * @author Андрей
 * */
public class CommandClear extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandClear(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        return collectionManager.clear();
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "clear: очистить коллекцию";
    }
}