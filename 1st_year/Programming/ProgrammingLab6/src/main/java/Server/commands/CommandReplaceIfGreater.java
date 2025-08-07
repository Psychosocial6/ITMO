package Server.commands;

import Server.collectionElements.Vehicle;
import Server.managers.CollectionManager;

/**
 * Класс команды замены объекта если больше
 * @author Андрей
 * */
public class CommandReplaceIfGreater extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandReplaceIfGreater(CollectionManager collectionManager) {
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
        return collectionManager.replaceIfGreater(key, element);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "replace_if_greater null {element}: заменить значение по ключу, если новое значение больше старого";
    }
}
