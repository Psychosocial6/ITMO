package commands;

import managers.CollectionManager;

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
        super(collectionManager, 0);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public void execute(Object[] args) {
        collectionManager.clear();
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