package Server.commands;

import Server.collectionElements.Vehicle;
import Server.managers.CollectionManager;

/**
 * Класс команды замены объекта если меньше
 * @author Андрей
 * */
public class CommandReplaceIfLower extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandReplaceIfLower(CollectionManager collectionManager) {
        super(collectionManager);
        requiresVehicleObject = true;
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        String key = (String) args[0];
        Vehicle element = (Vehicle) args[1];
        return collectionManager.replaceIfLower(key, element);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "replace_if_lower null {element}: заменить значение по ключу, если новое значение меньше старого";
    }
}
