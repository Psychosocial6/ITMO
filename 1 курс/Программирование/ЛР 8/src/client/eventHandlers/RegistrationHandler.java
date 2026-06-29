package client.eventHandlers;

import client.Client;
import client.ClientRequestSender;
import client.ClientResponseReceiver;
import client.GUI.MainPageGUI;
import client.GUI.RegistrationPageGUI;
import client.dataStorage.CurrentClient;
import client.interfaces.RegistrationHandlerInterface;

public class RegistrationHandler implements RegistrationHandlerInterface {
    private RegistrationPageGUI registrationPageGUI;
    private ClientRequestSender sender;
    private ClientResponseReceiver receiver;

    public RegistrationHandler(RegistrationPageGUI registrationPageGUI, ClientRequestSender sender, ClientResponseReceiver receiver) {
        this.registrationPageGUI = registrationPageGUI;
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void register(String login, String password) {
        try {
            String input = "register";
            sender.send(input);
            receiver.getResponce();

            sender.send(new String[]{login, password});

            String responce = (String) receiver.getData();

            if (responce.equals("OK")) {
                System.out.println("Welcome");
                Client.currentClient = new CurrentClient(login, password);
                registrationPageGUI.getWindow().dispose();
            }
            else {
                System.out.println(responce);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}