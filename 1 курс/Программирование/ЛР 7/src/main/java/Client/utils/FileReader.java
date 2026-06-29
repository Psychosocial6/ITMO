package Client.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Утилитный класс для чтения файлов
 * @author Андрей
 * */
public class FileReader {

    /**
     * Метод для чтения содержимого файла
     * @param file - файл из которого происходит чтение
     * @return String fileString - строка с содержимым файла
     * @throws FileNotFoundException
     */
    public static String readFile(File file) throws FileNotFoundException {
        StringBuilder fileString = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                fileString.append(scanner.nextLine() + "\n");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(String.format("File %s not found", file));
        }
        return fileString.toString();
    }
}