package client.dataInput;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Класс для чтения данных из файла.
 */
public class FileDataInput implements AutoCloseable, InputSource {
    private final BufferedInputStream bis;
    /**
     * Конструктор для инициализации объекта с путем к файлу.
     *
     * @param fileName путь к файлу, из которого будут читаться данные.
     */
    public FileDataInput(String fileName) {
        try {
            this.bis = new BufferedInputStream(new FileInputStream(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Ошибка: файл не найден - " + fileName, e);
        }
    }
    /**
     * Чтение строки из файла.
     *
     * @return строка из файла, или {@code null}, если достигнут конец файла.
     */
    @Override
    public String input() {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int byteRead;
            while ((byteRead = bis.read()) != -1) {
                if (byteRead == '\n') {
                    break;
                }
                buffer.write(byteRead);
            }
            return buffer.size() > 0 ? buffer.toString().trim() : null;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла", e);
        }
    }
    
    /**
     * Закрытие файла после завершения работы.
     */
    @Override
    public void close() {
        try {
            bis.close();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при закрытии файла", e);
        }
    }
	
}
