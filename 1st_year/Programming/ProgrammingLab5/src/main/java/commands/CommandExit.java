package commands;

import managers.CollectionManager;

/**
 * Класс команды выхода
 * @author Андрей
 * */
public class CommandExit extends Command {

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * */
    public CommandExit(CollectionManager collectionManager) {
        super(collectionManager, 0);
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public void execute(Object[] args) {
        collectionManager.exit();
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "exit: завершить программу (без сохранения в файл)";
    }
}
