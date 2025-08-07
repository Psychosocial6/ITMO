package Server.commands;

import Server.managers.CollectionManager;
import Server.utils.Invoker;

/**
 * Класс команды помощи
 * @author Андрей
 * */
public class CommandHelp extends Command{

    private Invoker invoker;
    /**
     * Конструктор
     * @param invoker - класс управляющий командами
     * */
    public CommandHelp(CollectionManager collectionManager, Invoker invoker) {
        super(collectionManager);
        this.invoker = invoker;
    }

    /**
     * Метод реализующий исполнение команды
     * @param args - массив аргументов команды
     * */
    @Override
    public String execute(Object[] args) {
        String msg = "";
        for (Object command: invoker.getCommands().values()) {
            msg += ((Command) command).getInfo() + "\n";
        }
        return msg;
    }

    /**
     * Метод возвращающий информацию о команде
     * @return String info
     * */
    @Override
    public String getInfo() {
        return "help: вывести справку по доступным командам";
    }
}
