package client;

import java.io.*;
import java.net.ConnectException;
import java.util.*;

import client.GUI.AddPageGUI;
import client.GUI.GuiOutput;
import client.GUI.MainPageGUI;
import client.GUI.RegistrationPageGUI;
import client.dataInput.DataInput;
import client.dataStorage.CollectionView;
import client.dataStorage.CurrentClient;
import client.dataStorage.DataForMovie;
import client.dataValidation.CheckData;
import client.dataValidation.CheckInput;
import client.dataValidation.CommandParser;
import client.eventHandlers.*;
import client.executeScript.ExecuteScript;
import client.executeScript.FileStack;
import client.other.PageParser;
import client.other.TableElement;
import com.sun.tools.javac.Main;


public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 2348;
    
    static DataInput dataInput = new DataInput();
    static CheckInput checkInput = new CheckInput();
    public static CurrentClient currentClient;
    public static Long pageCounter = 1L;
    public static  ClientResponseReceiver receiver;
    public static ClientRequestSender sender;
    public static MainPageGUI mainPageGUI;
    public static GuiOutput guiOutput = new GuiOutput();
    @SuppressWarnings("unchecked")
	public static void main(String[] args) {
        CommandParser commandParser = new CommandParser();
        ExecuteScript executeScript = new ExecuteScript();

        try {
        	ClientConnection connection = new ClientConnection();
        	connection.connect(SERVER_HOST, SERVER_PORT);
            sender = new ClientRequestSender(connection.getOut());
            receiver = new ClientResponseReceiver(connection.getIn());
            RegistrationPageGUI registrationPageGUI = new RegistrationPageGUI();
            RegistrationHandler registrationHandler = new RegistrationHandler(registrationPageGUI, sender, receiver);
            LoginHandler loginHandler = new LoginHandler(registrationPageGUI, sender, receiver);

            
            
            
            registrationPageGUI.setRegistrationHandler(registrationHandler);
            registrationPageGUI.setLoginHandler(loginHandler);

            registrationPageGUI.createAndShowWindow();


            new DataForMovie(receiver);
            sender.send(new Object[]{"load_next_page", new Object[]{1L}, currentClient.getUserName(), currentClient.getUserPassword()});
            //new CollectionView((HashMap<Long, String>) receiver.getResponce());
            //System.out.println(receiver.getData());
            
            //язык по умолчанию
            LangManager.setLanguage("english");
            
            List<Object> dataMovie =  (List<Object>) PageParser.parsePage((String) receiver.getData());
            ArrayList<TableElement> tableElements =(ArrayList<TableElement>) dataMovie.get(0);
            LinkedHashMap<Integer, Long> coordinates = (LinkedHashMap<Integer, Long>) dataMovie.get(1);
            ArrayList<String> createdBy =  (ArrayList<String>) dataMovie.get(2);
            CollectionView.setMovieCoordinates(coordinates);
            CollectionView.setCreatedBy(createdBy);
            
            mainPageGUI = new MainPageGUI(currentClient.getUserName(), tableElements);
            NextPageHandler nextPageHandler = new NextPageHandler(mainPageGUI, sender, receiver);
            SortingHandler sortingHandler = new SortingHandler(mainPageGUI, sender, receiver);
            FilterHandler filterHandler = new FilterHandler(mainPageGUI, sender, receiver);
            InfoHandler infoHandler = new InfoHandler(sender);
            UpdateCollectionHandler updateCollectionHandler = new UpdateCollectionHandler(sender);

            mainPageGUI.setNextPageHandler(nextPageHandler);
            mainPageGUI.setSortingHandler(sortingHandler);
            mainPageGUI.setFilterHandler(filterHandler);
            mainPageGUI.setInfoHandler(infoHandler);
            mainPageGUI.setUpdateCollectionHandler(updateCollectionHandler);
            mainPageGUI.createAndShowWindow();

            
            ServerPoller serverPoller = new ServerPoller(sender, currentClient.getUserName(), currentClient.getUserPassword());
            serverPoller.startPolling();
            Thread listenerThread = new Thread(new ClientListener(receiver));
            listenerThread.start();
            
            
            /*
            while (true) {
                System.out.println("> ");
                
                input = dataInput.input();

                if (input.trim().equalsIgnoreCase("exit")) {
                    System.out.println("Завершение работы клиента...");
                    
                    serverPoller.stopPolling();
                    listenerThread.interrupt();
                    break;
                }

                if (commandParser.parseCommandName(input)[0].equals("execute_script")) {
                    String scriptFileName = commandParser.parseCommandName(input)[1];
                    executeScript.executeScript(scriptFileName, connection.getOut(), connection.getIn(), DataForMovie.additionalInput);
                    FileStack.fileStack.clear();
                    continue;
                }

                String[] commandParts = commandParser.parseCommandName(input);
                String commandName = commandParts[0];

                Object[] arg;
                boolean needsAdditionalInput = DataForMovie.additionalInput.getOrDefault(commandName, false);
                boolean hasArgument = commandParts.length > 1;

                if (needsAdditionalInput && hasArgument) {
                	Long id =0L;
                	if(CheckData.isLong(commandParts[1])) {
                		id = Long.parseLong(commandParts[1]);
                	}
                	else {
                		System.out.println("Ошибка: параметр должен быть числом");
                		continue;
                	}
                	
                	Map<String, Object> movieData = new LinkedHashMap<>(checkInput.checkInput());
                    
                	arg = new Object[]{id, movieData};
                } else if (needsAdditionalInput) {
                    Map<String, Object> movieData = new LinkedHashMap<>(checkInput.checkInput());
                    arg = new Object[]{movieData};
                } else if (hasArgument) {
                	if(CheckData.isInteger(commandParts[1]) && commandParts.length <= 2) {
                		arg = new Object[]{Long.parseLong(commandParts[1])};
                	}
                	else if (hasArgument && commandParts.length > 2) {
                		String sortParam = "";
                		for(int i = 2; i <=commandParts.length-1; i++) {
                			sortParam = sortParam +  " " +commandParts[i];
                		}
                		arg = new Object[]{Long.parseLong(commandParts[1]), sortParam};
                	}
                	else {
                		System.out.println("Ошибка: параметр должен быть числом");
                		continue;
                	}
                } else {
                    arg = new Object[]{};
                }
                
                String login = currentClient.getUserName();
                String password = currentClient.getUserPassword();
                sender.send(new Object[]{commandName, arg, login, password});


            }
            */
        } 
        catch (ConnectException e) {
            System.out.println("Сервер занят, подождите немного.");
        }
        catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}

