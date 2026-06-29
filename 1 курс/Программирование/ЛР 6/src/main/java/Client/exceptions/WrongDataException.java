package Client.exceptions;

/**
 * Класс-исключение для неверных данных
 * @author Андрей
 * */
public class WrongDataException extends Exception {
    private String message;

    /**
     * Конструктор
     * @param message - сообщение получаемое через getMessage()
     * */
    public WrongDataException(String message) {
        this.message = message;
    }

    /**
     * Метод возвращающий сообщение об ошибке
     * @return String message - сообщение
     * */
    @Override
    public String getMessage() {
        return message;
    }
}