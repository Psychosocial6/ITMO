package Client.managers;

import Client.Client;
import Common.requests.Request;
import Client.utils.FileReader;
import Client.utils.VehicleReader;
import Common.collectionElements.Vehicle;
import Common.reponses.Response;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Класс управляющий чтением консоли
 * @author Андрей
 * */
public class ConsoleManager {

    private RequestManager requestManager;
    private Client client;
    private boolean registered = false;
    private String userName;
    private String password;
    private int userID;


    /**
     * Конструктор
     * */
    public ConsoleManager(Client client) {
        requestManager = new RequestManager();
        this.client = client;
    }

    /**
     * Метод для чтения и обработки консоли
     * */
    public void readConsole() {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!registered) {
                System.out.println("Для регистрации ввести имя и пароль пользователя, разделить пробелом:");
                while (!registered) {
                    try {
                        String[] input = scanner.nextLine().split(" ");
                        Request request = requestManager.getRequest("register");
                        Object[] args = new Object[request.getSimpleArgumentsRequired()];
                        for (int i = 0; i < args.length; i++) {
                            args[i] = input[i];
                        }
                        userName = (String) args[0];
                        password = (String) args[1];
                        request.setArgs(args);
                        Response response = client.sendRequestAndGetResponse(request);
                        if (response.getResultMessage().isEmpty() || response.getResultMessage().equals("Неверный пароль")) {
                            System.out.println("Ошибка, попробуйте снова");
                            System.out.println(response.getErrorMessage());
                            System.out.println(response.getResultMessage());
                        }
                        else {
                            System.out.println(response.getResultMessage());
                            String[] s = response.getResultMessage().split(" ");
                            userID = Integer.parseInt(s[s.length - 1]);
                            registered = true;
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Кривой ввод!");
                    }

                }
            }

            String[] input;

            if (scanner.hasNext()) {

                input = scanner.nextLine().split("\s+");
                String commandToken = input[0];

                if (requestManager.getRequests().containsKey(commandToken)) {

                    int simpleArgumentsRequired = requestManager.getRequest(commandToken).getSimpleArgumentsRequired();
                    int vehiclesRequired = 0;

                    if (requestManager.getRequest(commandToken).requiresVehicle()) {
                        vehiclesRequired = 1;
                    }

                    if (input.length - 1 != simpleArgumentsRequired) {
                        System.out.println(String.format("Error, got %d simple arguments, %d required", input.length - 1, simpleArgumentsRequired));
                    }


                    else {
                        Object[] args = new Object[simpleArgumentsRequired + vehiclesRequired + 1];
                        for (int i = 0; i < simpleArgumentsRequired; i++) {
                            args[i] = input[i + 1];
                        }

                        if (requestManager.getRequest(commandToken).requiresVehicle()) {
                            Vehicle vehicle = VehicleReader.readVehicle(scanner);
                            vehicle.setOwnerID(userID);
                            args[args.length - 2] = vehicle;
                        }
                        args[args.length - 1] = userName;

                        if (commandToken.equals("execute_script")) {
                            File file = new File(input[1]);
                            String data = "";
                            try {
                                data = FileReader.readFile(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            args[0] = data;
                            args[1] = userName;
                        }

                        Request request = requestManager.getRequest(commandToken);
                        request.setArgs(args);
                        request.setLogin(userName);
                        request.setPassword(password);
                        Response response = client.sendRequestAndGetResponse(request);
                        System.out.println(response.getErrorMessage());
                        System.out.println(response.getResultMessage());
                    }
                } else if (commandToken.equals("exit")) {
                    System.exit(0);
                } else if (!commandToken.isEmpty()) {
                    //throw new CommandTokenException(String.format("Wrong command token, command {%s} doesn't exist", commandToken));
                    System.out.println(String.format("Wrong command token, command {%s} doesn't exist", commandToken));
                }
            }
        }
    }
}

