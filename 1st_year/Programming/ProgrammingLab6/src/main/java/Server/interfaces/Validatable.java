package Server.interfaces;

import Server.exceptions.WrongDataException;

/**
 * Интерфейс для валидации введенных полей
 * @author Андрей
 * */
public interface Validatable {
    /**
     * Метод для проверки полей
     * @throws WrongDataException - исключение уведомляющее о неверных данных
     * */
    void validate() throws WrongDataException;
}