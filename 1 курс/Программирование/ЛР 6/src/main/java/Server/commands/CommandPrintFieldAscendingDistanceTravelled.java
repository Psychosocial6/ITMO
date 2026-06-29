package Server.commands;

import Server.managers.CollectionManager;

/**
 * Класс команды вывода поля distaneTravelled в порядке возрастания
 * @author Андрей
 * */
public class CommandPrintFieldAscendingDistanceTravelled extends  Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandPrintFieldAscendingDistanceTravelled(CollectionManager collectionManager) {
        super(collectionManager);
    }

       /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        return collectionManager.printFieldAscendingDistanceTravelled();
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "print_field_ascending_distance_travelled: вывести значения поля distanceTravelled всех элементов в порядке возрастания";
    }
}
