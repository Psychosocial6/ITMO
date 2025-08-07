package managers;

import collectionElements.Vehicle;
import commands.Command;
import exceptions.ExecutionException;
import utils.Invoker;
import utils.VehicleReader;

import java.util.Scanner;

/**
 * Класс управляющий чтением консоли
 * @author Андрей
 * */
public class ConsoleManager {

    private Invoker invoker;

    /**
     * Конструктор
     * @param invoker - класс управляющий командами
     * */
    public ConsoleManager(Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Метод для чтения и обработки консоли
     * */
    public void readConsole() {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            String[] input;

            if (scanner.hasNext()) {

                input = scanner.nextLine().split("\s+");
                String commandToken = input[0];

                if (invoker.getCommands().containsKey(commandToken)) {

                    int simpleArgumentsRequired = invoker.getCommands().get(commandToken).getSimpleArgumentsRequired();
                    int vehiclesRequired = 0;
                    if (invoker.getCommands().get(commandToken).requiresVehicleObject) {
                        vehiclesRequired = 1;
                    }

                    if (input.length - 1 != simpleArgumentsRequired) {
                        System.out.println(String.format("Error, got %d simple arguments, %d required", input.length - 1, simpleArgumentsRequired));
                    }
                    else {

                        Object[] args = new Object[simpleArgumentsRequired + vehiclesRequired];
                        for (int i = 0; i < simpleArgumentsRequired; i++) {
                            args[i] = input[i + 1];
                        }

                        if (invoker.getCommands().get(commandToken).requiresVehicleObject) {
                            Vehicle vehicle = VehicleReader.readVehicle(scanner);
                            args[args.length - 1] = vehicle;
                        }

                        if (commandToken.equals("help")) {
                            args = new Object[invoker.getCommands().size()];
                            int index = 0;
                            for (Command command : invoker.getCommands().values()) {
                                args[index] = command;
                                index++;
                            }
                        }
                        try {
                            invoker.executeCommandUsingToken(commandToken, args);
                        } catch (ExecutionException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                }
                else if (!commandToken.isEmpty()) {
                    //throw new CommandTokenException(String.format("Wrong command token, command {%s} doesn't exist", commandToken));
                    System.out.println(String.format("Wrong command token, command {%s} doesn't exist", commandToken));
                }
            }
        }
    }
}
