package server;

import java.io.*;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;
import java.util.concurrent.ExecutorService;

import server.comands.ExecuteCommand;
import server.dataStorage.CommandRegistry;
import server.dataStorage.DataFactory;
import server.dataStorage.EnumDataFactory;


public class RequestHandler {
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ResponseSender responseSender;
    private final ExecutorService processPool;
    private final ExecutorService responsePool;
    
    private Map<String, Boolean> additionalInputForCommand;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    public RequestHandler(SocketChannel clientChannel, ExecutorService processPool, ExecutorService responsePool) throws IOException {
    	
    	this.processPool = processPool;
        this.responsePool = responsePool;
        
        out = new ObjectOutputStream(clientChannel.socket().getOutputStream());
        in = new ObjectInputStream(clientChannel.socket().getInputStream());
        responseSender = new ResponseSender(out);

        
    }

    public void handleClient() throws SQLException {
        try {
        	
        	while(true) {
        		String entrance = (String) in.readObject();
        		if (entrance.trim().toLowerCase().equals("join")) {
        			responseSender.sendResponse("Введите логин и пароль");
        			String[] credentials = (String[]) in.readObject();
        			String login = credentials[0];
        			String password = credentials[1];
        			
        			if (Server.allUsers.authenticate(login, password)) {
        		        responseSender.sendResponse("OK");
        		        break;
        		    } else {
        		        responseSender.sendResponse("Неверный логин или пароль");
        		    }
        		}
        		else if (entrance.trim().toLowerCase().equals("register")){
        			responseSender.sendResponse("Введите логин и пароль");
        			String[] credentials = (String[]) in.readObject();
        			String login = credentials[0];
        			String password = credentials[1];
        			if (Server.allUsers.registerUser(login, password)) {
        				Server.userManager.registerUser(login, password);
        		        responseSender.sendResponse("OK");
        		        break;
        		    } else {
        		        responseSender.sendResponse("Пользователь уже существует");
        		    }
        			
        		}
        		else {
        			responseSender.sendResponse("Введена неверная команда");
        		}
        		
        	}
            
            additionalInputForCommand = new CommandRegistry().getCommandsWithInputRequirement();
            responseSender.sendResponse(additionalInputForCommand);
            logger.info("Список команд отправлен клиенту.");
            
            DataFactory enumFactory = new EnumDataFactory();
            responseSender.sendResponse(enumFactory.createData());
            logger.info("Фабрика данных отправлена клиенту.");
 
            //responseSender.sendResponse(Server.movieManager.loadNextPage((long) 1));
            //logger.info("Первая страница с фильмами отправленна клиенту");
            
            while (true) {
                Object received = in.readObject();
                if (received instanceof Object[]) {
                    Object[] receivedArray = (Object[]) received;

                    if (receivedArray.length != 4 || !(receivedArray[0] instanceof String) || !(receivedArray[1] instanceof Object[])) {
                        responseSender.sendResponse("Ошибка: Некорректный формат команды.");
                        logger.warning("Некорректный формат команды от клиента.");
                        continue;
                    }

                    String commandName = (String) receivedArray[0];
                    Object[] commandArgs = (Object[]) receivedArray[1];
                    String login = (String) receivedArray[2];
                    String password = (String) receivedArray[3];
                    
                    System.out.println("Получена команда: " + commandName);
                    logger.info("Получена команда от клиента: " + commandName);
                    
                    // Выполняем команду
                    processPool.execute(() -> {
                        try {
                            String response = ExecuteCommand.executeCommand(commandName, commandArgs, login, password);
                            logger.info("Команда выполнена: " + commandName);

                            responsePool.execute(() -> {
                                try {
                                    responseSender.sendResponse(response);
                                } catch (IOException e) {
                                    logger.warning("Ошибка при отправке ответа: " + e.getMessage());
                                }
                            });

                        } catch (Exception e) {
                            logger.warning("Ошибка при обработке команды: " + e.getMessage());
                        }
                    });
                } else {
                    System.out.println("Ошибка: получен некорректный объект.");
                    logger.warning("Получен некорректный объект от клиента.");
                }
            }
        } catch (EOFException e) {
            System.out.println("Клиент отключился.");
            logger.info("Клиент отключился.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ошибка при обработке клиента: " + e.getMessage());
            logger.severe("Ошибка при обработке клиента: " + e.getMessage());
        }
    }


}
