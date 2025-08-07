package interfaces;

import collectionElements.Vehicle;

/**
 * Интерфес команд
 * @author Андрей
 * */
public interface CommandInterface {
    /**
     * Метод реализующий исполнение команды
     * @param args - аргументы команды
     * */
    void execute(Object[] args);

    /**
     * Метод возвращающий информацию о команде
     * @return String info - информация
     */
    String getInfo();
}