package Server.commands;

import Server.managers.CollectionManager;

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
        super(collectionManager);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
       return collectionManager.info();
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
