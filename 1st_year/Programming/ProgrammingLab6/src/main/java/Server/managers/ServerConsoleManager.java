package Server.managers;

import Server.exceptions.ExecutionException;
import Server.utils.Invoker;

import java.util.Scanner;

public class ServerConsoleManager {
    private Invoker invoker;

    public ServerConsoleManager(Invoker invoker) {
        this.invoker = invoker;
    }

    public void readConsole() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] input;

            if (scanner.hasNext()) {

                input = scanner.nextLine().split("\s+");
                String commandToken = input[0];

                Object[] args;
                switch (commandToken) {
                    case "exit":
                        args = new Object[]{"src/main/java/Server/files/savedCollection.xml"};
                        try {
                            invoker.executeCommandUsingToken("save", args);
                            System.out.println("Saved successfully");
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        System.exit(0);
                        break;
                    case "save":
                        args = new Object[]{"src/main/java/Server/files/savedCollection.xml"};
                        try {
                            invoker.executeCommandUsingToken("save", args);
                            System.out.println("Saved successfully");
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                }
            }
        }
    }
}
