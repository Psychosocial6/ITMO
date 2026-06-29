package Server.commands;

import Server.managers.CollectionManager;

import java.io.File;

/**
 * Класс команды сохранения коллекции в файл
 * @author Андрей
 * */
public class CommandSave extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandSave(CollectionManager collectionManager) {
        super(collectionManager);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        File file = new File((String)args[0]);
        return collectionManager.save(file);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "save: сохранить коллекцию в файл";
    }
}
