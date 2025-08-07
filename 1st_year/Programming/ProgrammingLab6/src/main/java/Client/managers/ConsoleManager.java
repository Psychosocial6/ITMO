package Client.managers;

import Client.Client;
import Client.requests.Request;
import Client.utils.FileReader;
import Client.utils.VehicleReader;
import Client.collectionElements.Vehicle;
import Client.responses.Response;


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
                        Object[] args = new Object[simpleArgumentsRequired + vehiclesRequired];
                        for (int i = 0; i < simpleArgumentsRequired; i++) {
                            args[i] = input[i + 1];
                        }

                        if (requestManager.getRequest(commandToken).requiresVehicle()) {
                            Vehicle vehicle = VehicleReader.readVehicle(scanner);
                            args[args.length - 1] = vehicle;
                        }

                        if (commandToken.equals("execute_script")) {
                            File file = new File(input[1]);
                            String data = "";
                            try {
                                data = FileReader.readFile(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            args[0] = data;
                        }

                        Request request = requestManager.getRequest(commandToken);
                        request.setArgs(args);
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

