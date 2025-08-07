package client.executeScript;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import client.Client;
import client.dataValidation.CheckInputFile;
import client.dataValidation.CommandParser;
import client.dataValidation.InputChecker;

public class ExecuteScript {
    InputChecker checkInputFile = new CheckInputFile();
    CommandParser commandParser = new CommandParser();
    public static ArrayList<ArrayList<String>> collectionOfMultipleFiles = new ArrayList<>();

    public void executeScript(String file, ObjectOutputStream out, ObjectInputStream in, Map<String, Boolean> additionalInput) {
        CollectionCommandsInFile collectionCommandInFile = new CollectionCommandsInFile();

        try {
            ArrayList<String> fileCommands = collectionCommandInFile.readComands(file);
            collectionOfMultipleFiles.add(fileCommands); 
            
            for (String oldFile : FileStack.fileStack) {
                if (file.equals(oldFile)) {
                    System.out.println("команда execute_script не выполнена из-за рекурсии в ваших файлах, имя файла: " + file);
                    collectionOfMultipleFiles.clear();
                    return;
                }
            }
            FileStack.fileStack.add(file);

            while (!collectionOfMultipleFiles.isEmpty()) {
                ArrayList<String> currentFileCommands = collectionOfMultipleFiles.get(collectionOfMultipleFiles.size() - 1);

                while (!currentFileCommands.isEmpty()) {
                    String command = currentFileCommands.remove(0); 
                    
                    String[] commandParts = commandParser.parseCommandName(command);
                    String commandName = commandParts[0];

                    if (commandName.equals("execute_script")) {
                        String scriptFileName = commandParts[1];

                        if (FileStack.fileStack.contains(scriptFileName)) { 
                            System.out.println("команда execute_script не выполнена из-за рекурсии в ваших файлах, имя файла: " + scriptFileName);
                            continue; 
                        }

                        // Читаем команды нового файла и добавляем в список
                        ArrayList<String> newFileCommands = collectionCommandInFile.readComands(scriptFileName);
                        collectionOfMultipleFiles.add(newFileCommands);
                        FileStack.fileStack.add(scriptFileName);
                        
                        break;
                    }

                    Object[] arg;
                    boolean needsAdditionalInput = additionalInput.getOrDefault(commandName, false);
                    boolean hasArgument = commandParts.length > 1;

                    if (needsAdditionalInput && hasArgument) {
                        Long id = Long.parseLong(commandParts[1]);
                        Map<String, Object> movieData = new LinkedHashMap<>(checkInputFile.checkInput());
                        arg = new Object[]{id, movieData};
                    } else if (needsAdditionalInput) {
                        Map<String, Object> movieData = new LinkedHashMap<>(checkInputFile.checkInput());
                        arg = new Object[]{movieData};
                    } else if (hasArgument) {
                        arg = new Object[]{Long.parseLong(commandParts[1])};
                    } else {
                        arg = new Object[]{};
                    }
                    String login = Client.currentClient.getUserName();
                    String password = Client.currentClient.getUserPassword();
                    try {
                        out.writeObject(new Object[]{commandName, arg, login, password});
                        out.flush();

                        String response = (String) in.readObject();
                        System.out.println("Ответ от сервера: " + response);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                if(collectionOfMultipleFiles.size()>0 && collectionOfMultipleFiles.get(collectionOfMultipleFiles.size()-1).size() == 0){
		    		collectionOfMultipleFiles.remove(collectionOfMultipleFiles.size()-1);
		    	}
            }
        } catch (RuntimeException e) {
            System.out.println("Ошибка: файл не найден");
        }
    }
    

    
}
