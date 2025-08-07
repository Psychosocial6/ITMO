package Server.exceptions;

/**
 * Класс-исключение для ошибки исполнения скрипта
 * @author Андрей
 * */
public class ScriptExecutionException extends Exception {
    private String message;

    /**
     * Конструктор
     * @param message - сообщение получаемое через getMessage()
     * */
    public ScriptExecutionException(String message) {
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