package Server.utils;

import Server.collectionElements.Coordinates;
import Server.collectionElements.FuelType;
import Server.collectionElements.Vehicle;
import Server.commands.Command;
import Server.exceptions.CommandTokenException;
import Server.exceptions.ExecutionException;
import Server.exceptions.ScriptExecutionException;
import Server.exceptions.WrongDataException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Класс исполняющий скрипт из файла
 * @author Андрей
 * */
public class ScriptExecutor {
    private Invoker invoker;

    /**
     * Конструктор
     * @param invoker - исполнитель команд
     */
    public ScriptExecutor(Invoker invoker) {
        this.invoker = invoker;
    }

    /**
     * Метод исполняющий скрипт из файла
     * @param file - файл со скриптом
     * @throws ScriptExecutionException - исключение при ошибке исполнения скрипта
     */
    public String executeScript(String script) throws ScriptExecutionException {
        Scanner scanner = null;
        String msg = "";
        //System.out.println(script);
        File file = new File("src/main/java/Server/files/execution.txt");
        FileWriter.writeIntoFile(file, script);
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            return "Error while executing script, file not found";
        }
        while (scanner.hasNext()) {
            String[] input;
            if (scanner.hasNext()) {
                input = scanner.nextLine().split("\s+");
                String commandToken = input[0];
                if (invoker.getCommands().containsKey(commandToken)) {
                    Object[] args;
                    if (invoker.getCommands().get(commandToken).requiresVehicleObject) {
                        args = new Object[input.length];
                        boolean incorrectData = true;
                        while (incorrectData) {
                            String vehicleName = scanner.nextLine();
                            if (vehicleName.isEmpty()) {
                                throw new ScriptExecutionException("Empty vehicle name");
                            }

                            Integer x;
                            try {
                                x = scanner.nextInt();
                            } catch (InputMismatchException e) {
                                throw new ScriptExecutionException("Wrong x coordinate");
                            }

                            Long y;
                            try {
                                y = scanner.nextLong();
                            } catch (InputMismatchException e) {
                                throw new ScriptExecutionException("Wrong y coordinate");
                            }

                            Long enginePower = null;
                            String pow = scanner.nextLine();
                            if (!pow.isEmpty()) {
                                try {
                                    enginePower = Long.parseLong(pow);
                                } catch (InputMismatchException | NumberFormatException e) {
                                    throw new ScriptExecutionException("Wrong engine power");
                                }
                            }

                            double capacity;
                            try {
                                capacity = scanner.nextDouble();
                            } catch (InputMismatchException | NumberFormatException e) {
                                throw new ScriptExecutionException("Wrong capacity");
                            }

                            Long distanceTravelled = null;
                            String distTr = scanner.nextLine();
                            if (!distTr.isEmpty()) {
                                try {
                                    distanceTravelled = Long.parseLong(distTr);
                                } catch (InputMismatchException e) {
                                    throw new ScriptExecutionException("Wrong distanceTravelled");
                                }
                            }

                            FuelType fuelType;
                            String fuel = scanner.nextLine();
                            try {
                                fuelType = FuelType.valueOf(fuel);
                            } catch (IllegalArgumentException e) {
                                throw new ScriptExecutionException("Wrong fuel");
                            }

                            Coordinates coordinates = new Coordinates(x, y);
                            Vehicle vehicle = new Vehicle(vehicleName, coordinates, enginePower, capacity, distanceTravelled, fuelType);
                            try {
                                vehicle.validate();
                            } catch (WrongDataException e) {
                                throw new ScriptExecutionException("Wrong fields values");
                            }
                            for (int i = 1; i < input.length; i++) {
                                args[i - 1] = input[i];
                            }
                            args[args.length - 1] = vehicle;
                        }
                    } else {
                        args = new Object[input.length - 1];
                        for (int i = 1; i < input.length; i++) {
                            args[i - 1] = input[i];
                        }
                    }
                    if (commandToken.equals("help")) {
                        args = new Object[16];
                        int index = 0;
                        for (Command command : invoker.getCommands().values()) {
                            args[index] = command;
                            index++;
                        }

                    }
                    try {
                        msg += invoker.executeCommandUsingToken(commandToken, args);
                    } catch (ExecutionException e) {
                        return e.getMessage();
                    }
                } else if (!commandToken.isEmpty()) {
                    throw new CommandTokenException(String.format("Wrong command token, command {%s} doesn't exist", commandToken));
                }
            }
        }
        return msg;
    }
}