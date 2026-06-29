package Server.commands;

import Server.managers.CollectionManager;

/**
 * Класс команды фильтра по подстроке в имени
 * @author Андрей
 * */
public class CommandFilterContainsName extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandFilterContainsName(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        String name = (String) args[0];
         return collectionManager.filterContainsName(name);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "filter_contains_name name: вывести элементы, значение поля name которых содержит заданную подстроку";
    }
}
