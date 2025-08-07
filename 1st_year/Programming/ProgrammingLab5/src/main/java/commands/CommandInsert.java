package commands;

import collectionElements.Vehicle;
import managers.CollectionManager;

/**
 * Класс команды вставки элемента
 * @author Андрей
 * */
public class CommandInsert extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandInsert(CollectionManager collectionManager) {
        super(collectionManager, 1);
        requiresVehicleObject = true;
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public void execute(Object[] args) { //key, elem
        String key = (String) args[0];
        Vehicle value = (Vehicle) args[1];
        collectionManager.insert(key, value);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "insert null {element}: добавить в коллекцию новый элемент с заданным ключом";
    }
}
