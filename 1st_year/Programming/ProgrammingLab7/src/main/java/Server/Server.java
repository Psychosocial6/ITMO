package Server;

import Server.managers.CollectionManager;
import Server.managers.DatabaseManager;
import Server.managers.ServerConsoleManager;
import Server.threads.ConsoleThread;
import Server.utils.Invoker;

public class Server {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.create();
        CollectionManager collectionManager = new CollectionManager(databaseManager);
        Invoker invoker = new Invoker(collectionManager);
        UDPServer udpServer = new UDPServer(2222, 65507, invoker);
        ServerConsoleManager consoleManager = new ServerConsoleManager(invoker, databaseManager);
        ConsoleThread consoleThread = new ConsoleThread(consoleManager);
        consoleThread.start();
        udpServer.startServer();
    }
}
