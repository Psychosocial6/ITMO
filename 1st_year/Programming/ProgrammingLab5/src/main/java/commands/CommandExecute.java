package commands;

import managers.CollectionManager;
import utils.Invoker;
import utils.ScriptExecutor;

import java.io.File;

/**
 * Класс команды исполнения скрипта
 * @author Андрей
 * */
public class CommandExecute extends Command {
    private Invoker invoker;

    /**
     * Конструктор
     * @param collectionManager - класс управляющий коллекцией
     * @param invoker - класс управляющий командами
     * */
    public CommandExecute(CollectionManager collectionManager, Invoker invoker) {
        super(collectionManager, 1);
        this.invoker = invoker;
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public void execute(Object[] args) {
        File file = new File((String) args[0]);
        collectionManager.executeScript(file, invoker);
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "execute_script file_name: считать и исполнить скрипт из указанного файла";
    }
}