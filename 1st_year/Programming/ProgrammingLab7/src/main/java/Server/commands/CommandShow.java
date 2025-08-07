package Server.commands;

import Server.managers.CollectionManager;

/**
 * Класс команды вывода коллекции
 * @author Андрей
 * */
public class CommandShow extends  Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandShow(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        return collectionManager.show();
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
