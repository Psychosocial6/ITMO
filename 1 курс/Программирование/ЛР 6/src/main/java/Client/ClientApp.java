package Client;

import Client.managers.ConsoleManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientApp {
    public static void main(String[] args) {
        ConsoleManager consoleManager = null;
        try {
            consoleManager = new ConsoleManager(new Client(2222, InetAddress.getByName("localhost")));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        consoleManager.readConsole();
    }

}
