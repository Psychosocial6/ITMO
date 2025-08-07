package server;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.logging.*;

import server.dataStorage.IdGeneration;
import server.dataStorage.MovieCollection;
import server.database.DatabaseMovieManager;
import server.database.DatabaseUserManager;
import server.user.UserManager;

import java.util.concurrent.*;


public class Server {
    private static final int PORT = 2348;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    
    // Пулы потоков
    private static final ExecutorService readPool = Executors.newFixedThreadPool(10); // Чтение
    private static final ExecutorService processPool = Executors.newCachedThreadPool(); // Обработка
    private static final ExecutorService responsePool = Executors.newCachedThreadPool(); // Ответы
    
    //static final UserManager userManager = new UserManager();
    static String dbUrl = "jdbc:postgresql://localhost:5433/studs";
    static String dbUser = "s467846";
    static String dbPassword = "S7KoN69grNgAcrHc";

    // Объекты для взаимодействия с БД
    public static DatabaseUserManager userManager = new DatabaseUserManager(dbUrl, dbUser, dbPassword);
    public static DatabaseMovieManager movieManager = new DatabaseMovieManager(userManager.getConnection());
    public static IdGeneration idGeneration = new  IdGeneration(userManager.getConnection());
    
    public static UserManager allUsers;
    
    public static void main(String[] args) {
        try {
        	setupLogger();
            logger.info("Сервер запускается...");
            
            try {
				MovieCollection movies = new MovieCollection(movieManager.loadAllMovies());
				allUsers = new UserManager(userManager.loadAllUsers());
			} catch (SQLException e) {

				System.out.println("Ошибка при загрузке данных из БД: "+  e.getMessage());
			}
            ConnectionHandler connectionHandler = new ConnectionHandler(PORT);
            
            
            while (true) {
                SocketChannel clientChannel = connectionHandler.acceptClient();
                if (clientChannel != null) {
                    System.out.println("Подключился новый клиент.");
                    logger.info("Подключился новый клиент: " + clientChannel.getRemoteAddress());
                    
                    readPool.execute(() -> {
                        try {
                            RequestHandler handler = new RequestHandler(clientChannel, processPool, responsePool);
                            handler.handleClient();
                        } catch (IOException | SQLException e) {
                            logger.warning("Ошибка при создании RequestHandler: " + e.getMessage());
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    private static void setupLogger() throws IOException {
    	ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);

        // Устанавливаем файл для логирования
        FileHandler fileHandler = new FileHandler("Files/server.log", true);
        fileHandler.setLevel(Level.ALL);
        logger.addHandler(fileHandler);

        // Устанавливаем формат логов
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);

        // Устанавливаем уровень логирования для самого логгера
        logger.setLevel(Level.ALL);
    }
}


