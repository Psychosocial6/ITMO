package commands;

import collectionElements.Vehicle;
import managers.CollectionManager;

/**
 * Класс команды удаления объектов меньше данного
 * @author Андрей
 * */
public class CommandRemoveLower extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandRemoveLower(CollectionManager collectionManager) {
        super(collectionManager, 0);
        requiresVehicleObject = true;
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public void execute(Object[] args) {
        Vehicle element = (Vehicle) args[0];
        collectionManager.removeIfLower(element);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "remove_lower {element}: удалить из коллекции все элементы, меньшие, чем заданный";
    }
}
