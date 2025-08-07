package Server.exceptions;

/**
 * Класс-исключение для неверного названия команды
 * @author Андрей
 * */
public class CommandTokenException extends RuntimeException {
    private String message;

    /**
     * Конструктор
     * @param message - сообщение получаемое через getMessage()
     * */
    public CommandTokenException(String message) {
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