package commands;

import managers.CollectionManager;

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
        super(collectionManager, 0);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public void execute(Object[] args) {
        collectionManager.show();
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
