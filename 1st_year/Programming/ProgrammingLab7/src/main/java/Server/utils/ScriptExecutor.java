package Server.utils;

import Common.collectionElements.Coordinates;
import Common.collectionElements.FuelType;
import Common.collectionElements.Vehicle;
import Server.commands.Command;
import Server.exceptions.CommandTokenException;
import Server.exceptions.ExecutionException;
import Server.exceptions.ScriptExecutionException;
import Common.exceptions.WrongDataException;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZonedDateTime;
import java.util.HashSet;
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
     * @param script - скрипт
     * @throws ScriptExecutionException - исключение при ошибке исполнения скрипта
     */
    public String executeScript(String script, String login) throws ScriptExecutionException {
        Scanner scanner = null;
        String msg = "";
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
                        args = new Object[input.length + 1];
                            String vehicleName = scanner.nextLine();
                            if (vehicleName.isEmpty()) {
                                throw new ScriptExecutionException("Empty vehicle name");
                            }

                            Integer x;
                            String line = scanner.nextLine();
                            try {
                                x = Integer.parseInt(line);
                            } catch (InputMismatchException e) {
                                throw new ScriptExecutionException("Wrong x coordinate");
                            }

                            Long y;
                            line = scanner.nextLine();
                            try {
                                y = Long.parseLong(line);
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
                            line = scanner.nextLine();
                            try {
                                capacity = Double.parseDouble(line);
                            } catch (InputMismatchException | NumberFormatException e) {
                                throw new ScriptExecutionException("Wrong capacity");
                            }

                            Long distanceTravelled = null;
                            line = scanner.nextLine();
                            if (!line.isEmpty()) {
                                try {
                                    distanceTravelled = Long.parseLong(line);
                                } catch (InputMismatchException e) {
                                    throw new ScriptExecutionException("Wrong distanceTravelled");
                                }
                            }

                            FuelType fuelType;
                            String fuel = scanner.nextLine();
                            try {
                                if (fuel.matches("\\d+")) {
                                    fuelType = FuelType.values()[Integer.parseInt(fuel) - 1];
                                }
                                else {
                                    fuelType = FuelType.valueOf(fuel);
                                }
                            } catch (IllegalArgumentException e) {
                                throw new ScriptExecutionException("Wrong fuel");
                            }

                            Coordinates coordinates = new Coordinates(x, y);
                            Vehicle vehicle = new Vehicle(vehicleName, coordinates, enginePower, capacity, distanceTravelled, fuelType);
                            vehicle.setCreationDate(ZonedDateTime.now());
                            try {
                                vehicle.validate();
                            } catch (WrongDataException e) {
                                throw new ScriptExecutionException("Wrong fields values");
                            }
                            for (int i = 1; i < input.length; i++) {
                                args[i - 1] = input[i];
                            }
                            args[args.length - 2] = vehicle;
                    } else {
                        args = new Object[input.length];
                        for (int i = 1; i < input.length; i++) {
                            args[i - 1] = input[i];
                        }

                    }
                    args[args.length - 1] = login;

                    if (commandToken.equals("help")) {
                        args = new Object[16];
                        int index = 0;
                        for (Command command : invoker.getCommands().values()) {
                            args[index] = command;
                            index++;
                        }

                    }
                    try {
                        msg += invoker.executeCommandUsingToken(commandToken, args) + "\n";
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