package Server.commands;

import Server.interfaces.CommandInterface;
import Server.managers.CollectionManager;

/**
 * Базовый класс для всех команд
 * @author Андрей
 * */
public abstract class Command implements CommandInterface {

    protected static CollectionManager collectionManager;
    public boolean requiresVehicleObject;

    /**
     * Конструктор
     * @param collectionManager - класс, управялющий коллекцией
     *
     * */
    public Command(CollectionManager collectionManager) {
        Command.collectionManager = collectionManager;
        requiresVehicleObject = false;
    }

    /**
     * Метод возвращающий класс, управляющий коллекцией
     * @return collectionManager
     * */
    public static CollectionManager getCollectionManager() {
        return collectionManager;
    }
}
