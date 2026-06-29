package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

public class ConnectionHandler {
    private ServerSocketChannel serverChannel;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public ConnectionHandler(int port) throws IOException {
        serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        logger.info("Сервер запущен на порту " + port);
        System.out.println("Сервер запущен на порту " + port);
    }

    public SocketChannel acceptClient() throws IOException {
    	logger.info("Принято новое подключение от клиента: ");
        return serverChannel.accept(); 
        
    }
    
    
}
