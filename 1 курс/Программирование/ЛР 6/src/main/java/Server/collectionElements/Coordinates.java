package Server.collectionElements;

import Server.exceptions.WrongDataException;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * Класс для хранения координат транспортного средства
 * @author Андрей
 * */
public class Coordinates extends Element {
    @JacksonXmlProperty(localName = "x")
    private Integer x;
    @JacksonXmlProperty(localName = "y")
    private Long y;

    /**
     * Пустой конструктор
     * */
    public Coordinates() {
    }

    /**
     * Конструктор
     * @param x значение координаты Х
     * @param y значение координаты У
     * */
    public Coordinates(Integer x, Long y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод для проверки значений полей на допустимость значений
     * @throws WrongDataException - исключение уведомляющее о неверных данных
     * */
    @Override
    public void validate() throws WrongDataException {
        if (x == null || x > 232) {
            throw new WrongDataException("Wrong x coordinate");
        }
        if (y == null || y > 281) {
            throw new WrongDataException("Wrong y coordinate");
        }
    }

    /**
     * Метод, возвращающий поле x
     * @return x
     */
    public Integer getX() {
        return x;
    }

    /**
     * Метод, устанавливающий новое значение x
     * @param x - новое значение х
     */
    public void setX(Integer x) {
        this.x = x;
    }

    /**
     * Метод, возвращающий поле y
     * @return y
     */
    public Long getY() {
        return y;
    }

    /**
     * Метод, устанавливающий новое значение y
     * @param y - новое значение y
     */
    public void setY(Long y) {
        this.y = y;
    }

    /**
     * Метод, приводящий объект к строковому представлению
     * @return {x=..., y=...}
     * */
    @Override
    public String toString() {
        return String.format("{x=%d, y=%d}", x, y);
    }
}