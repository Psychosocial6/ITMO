package Server.commands;

import Server.collectionElements.Vehicle;
import Server.managers.CollectionManager;

/**
 * Класс команды обновления элемента по id
 * @author Андрей
 * */
public class CommandUpdate extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandUpdate(CollectionManager collectionManager) {
        super(collectionManager);
        requiresVehicleObject = true;
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        int id = Integer.parseInt((String)args[0]);
        Vehicle element = (Vehicle) args[1];
        return collectionManager.updateById(id, element);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "update id {element}: обновить значение элемента коллекции, id которого равен заданному";
    }
}
