package commands;

import collectionElements.Vehicle;
import managers.CollectionManager;

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
        super(collectionManager, 1);
        requiresVehicleObject = true;
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public void execute(Object[] args) {
        String key = (String) args[0];
        Vehicle element = (Vehicle) args[1];
        collectionManager.replaceIfLower(key, element);
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
