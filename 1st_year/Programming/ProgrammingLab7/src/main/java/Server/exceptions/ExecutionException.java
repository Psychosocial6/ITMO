package Server.exceptions;

/**
 * Класс-исключение для ошибки выполнения команды
 * @author Андрей
 * */
public class ExecutionException extends Exception {
    private String message;

    /**
     * Конструктор
     * @param message - сообщение получаемое через getMessage()
     * */
    public ExecutionException(String message) {
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