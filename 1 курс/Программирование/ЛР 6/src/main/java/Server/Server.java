package Server;

import Server.managers.CollectionManager;
import Server.managers.ServerConsoleManager;
import Server.threads.ConsoleThread;
import Server.utils.FileReader;
import Server.utils.Invoker;
import Server.utils.XMLDeserializer;

import java.io.File;
import java.io.FileNotFoundException;

public class Server {
    public static void main(String[] args) {
        File inputFile = new File("src/main/java/Server/files/input.xml");
        File savedFile = new File("src/main/java/Server/files/savedCollection.xml");
        CollectionManager collectionManager = new CollectionManager();
        try {
            collectionManager = XMLDeserializer.deserializeFromXML(FileReader.readFile(inputFile));
            CollectionManager collectionManager1 = XMLDeserializer.deserializeFromXML(FileReader.readFile(savedFile));
            if (!collectionManager1.getCollection().isEmpty()) {
                collectionManager = collectionManager1;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        Invoker invoker = new Invoker(collectionManager);
        UDPServer udpServer = new UDPServer(2222, 65507, invoker);
        ServerConsoleManager consoleManager = new ServerConsoleManager(invoker);
        ConsoleThread consoleThread = new ConsoleThread(consoleManager);
        consoleThread.start();
        udpServer.startServer();
    }
}
