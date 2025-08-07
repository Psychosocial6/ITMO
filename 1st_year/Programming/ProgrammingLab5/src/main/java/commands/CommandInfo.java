package commands;

import managers.CollectionManager;

/**
 * Класс команды информации
 * @author Андрей
 * */
public class CommandInfo extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandInfo(CollectionManager collectionManager) {
        super(collectionManager, 0);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public void execute(Object[] args) {
        collectionManager.info();
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "info: вывести в стандартный поток вывода информацию о коллекции";
    }
}
