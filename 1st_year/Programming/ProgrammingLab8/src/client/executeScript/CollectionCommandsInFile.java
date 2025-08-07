package client.executeScript;

import java.util.ArrayList;

import client.dataInput.FileDataInput;

/**
 * Класс для чтения команд из файла и их хранения в коллекции.
 * Предоставляет метод для чтения команд из указанного файла и сохранения их в список.
 * 
 */
public class CollectionCommandsInFile {
	/**
     * Список для хранения команд, прочитанных из файла.
     */
	 ArrayList<String> collectionComandsInFile = new ArrayList<>();
	/**
     * Метод для чтения команд из файла и их сохранения в коллекцию.
     * 
     * @param file Имя файла, из которого будут прочитаны команды.
     * @return Список строк, содержащий прочитанные команды.
     */
	public  ArrayList<String> readComands(String file){
		try (FileDataInput fileReader = new FileDataInput("Files/" + file)) {
            String line;
            while ((line = fileReader.input()) != null) {
            	collectionComandsInFile.add(line);
            }
        }
		
		return collectionComandsInFile;
		
	}
	
}
