package Server.commands;

import Server.managers.CollectionManager;

/**
 * Класс команды удаления по ключу
 * @author Андрей
 * */
public class CommandRemoveKey extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandRemoveKey(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        String key = (String) args[0];
        return collectionManager.removeByKey(key);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "remove_key null: удалить элемент из коллекции по его ключу";
    }
}
