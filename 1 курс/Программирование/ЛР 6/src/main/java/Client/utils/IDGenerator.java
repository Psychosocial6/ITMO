package Client.utils;

/**
 * Утилитный класс для генерации значения id
 * @author Андрей
 * */
public class IDGenerator {
    private static int id = 0;

    /**
     * Метод генерирующий новые id
     * @return int id - новое значение id
     */
    public static int getNewID() {
        id++;
        return id;
    }
}