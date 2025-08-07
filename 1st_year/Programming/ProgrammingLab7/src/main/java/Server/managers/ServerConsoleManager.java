package Server.managers;

import Server.exceptions.ExecutionException;
import Server.utils.Invoker;

import java.util.Scanner;

public class ServerConsoleManager {
    private Invoker invoker;
    private DatabaseManager databaseManager;

    public ServerConsoleManager(Invoker invoker, DatabaseManager databaseManager) {
        this.invoker = invoker;
        this.databaseManager = databaseManager;
    }

    public void readConsole() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] input;

            if (scanner.hasNext()) {

                input = scanner.nextLine().split("\s+");
                String commandToken = input[0];

                switch (commandToken) {
                    case "exit":
                        System.exit(0);
                        break;
                    case "drop":
                        databaseManager.dropDB();
                        break;
                }
            }
        }
    }
}

