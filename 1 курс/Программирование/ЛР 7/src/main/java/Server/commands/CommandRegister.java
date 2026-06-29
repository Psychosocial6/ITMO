package Server.commands;

import Server.managers.CollectionManager;

public class CommandRegister extends Command {
    /**
     * Конструктор
     *
     * @param collectionManager - класс, управялющий коллекцией
     */
    public CommandRegister(CollectionManager collectionManager) {
        super(collectionManager);
        requiresVehicleObject = false;
    }

    @Override
    public String execute(Object[] args) {
        String user = (String) args[0];
        String password = (String) args[1];
        return collectionManager.register(user, password);
    }

    @Override
    public String getInfo() {
        return "";
    }
}
