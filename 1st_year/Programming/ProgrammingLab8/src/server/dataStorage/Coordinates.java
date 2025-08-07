package server.dataStorage;

import java.io.Serializable;

/**
 * Класс {@code Coordinates} представляет собой координаты с двумя полями:
 * {@code x} и {@code y}.
 * Значение {@code x} должно быть больше -754, в противном случае будет выброшено исключение {@code IllegalArgumentException}.
 */
public class Coordinates implements Serializable{
	private static final long serialVersionUID = 1L;
	
    private int x; //Значение поля должно быть больше -754
    private long y;
    
    /**
     * Конструктор без параметров, создающий объект с координатами по умолчанию.
     */
    public Coordinates() {

    }
    
    /**
     * Конструктор, принимающий значения для {@code x} и {@code y}.
     * 
     * @param x Значение координаты {@code x}, должно быть больше -754.
     * @param y Значение координаты {@code y}.
     * @throws IllegalArgumentException Если {@code x} меньше или равно -754.
     */
    public Coordinates(int x, long y) {
        setX(x); 
        setY(y); 
    }
    /**
     * Получает значение координаты {@code x}.
     * 
     * @return Значение координаты {@code x}.
     */
	public int getX() {
		return x;
	}
	/**
     * Устанавливает значение координаты {@code x}.
     * 
     * @param x Значение координаты {@code x}, которое должно быть больше -754.
     * @throws IllegalArgumentException Если {@code x} меньше или равно -754.
     */
	public void setX(int x) {
		if (x<=-754) {
			throw new IllegalArgumentException("число должно быть больше -754");
		}
		else {
			this.x = x;
		}
	} 
	
	/**
     * Получает значение координаты {@code y}.
     * 
     * @return Значение координаты {@code y}.
     */
	public long getY() {
		return y;
	}
	/**
     * Устанавливает значение координаты {@code y}.
     * 
     * @param y Значение координаты {@code y}.
     */
	public void setY(long y) {
		this.y = y;
	}
    
    
    
}
