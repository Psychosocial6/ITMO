package Client.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Утилитный класс для записи файлов
 * @author Андрей
 * */
public class FileWriter {

    /**
     * Метод осуществляющий запись строки в файл
     * @param file - файл для записи
     * @param data - данные которые необходимо записать
     */
    public static void writeIntoFile(File file, String data) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            byte[] buffer = data.getBytes(StandardCharsets.UTF_8);
            bufferedOutputStream.write(buffer, 0, buffer.length);
            bufferedOutputStream.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error while writing into file, file not found");
        } catch (IOException e) {
            System.out.println("Writing error");
        }
    }
}