package server.dataStorage;

import java.io.Serializable;

/**
 * Класс, представляющий собой местоположение с координатами и именем.
 * Содержит информацию о трехмерных координатах (X, Y, Z) и имени.
 */
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private Integer x; 
    private Long y; 
    private int z;
    private String name; 
    public Location() {
    	
    }
    public Location(Integer x, Long y, int z, String name) {
    	setX(x);
    	setY(y);
    	setZ(z);
    	setName(name);
    }
    /**
     * Возвращает значение координаты X.
     *
     * @return координата X.
     */
	public Integer getX() {
		return x;
	}
	/**
     * Устанавливает значение координаты X.
     *
     * @param x координата X.
     */
	public void setX(Integer x) {
		this.x = x;
	}
	
	/**
     * Возвращает значение координаты Y.
     *
     * @return координата Y.
     */
	public Long getY() {
		return y;
	}
	/**
     * Устанавливает значение координаты Y.
     *
     * @param y координата Y.
     */
	public void setY(Long y) {
		this.y = y;
	}
	
	/**
     * Возвращает значение координаты Z.
     *
     * @return координата Z.
     */
	public int getZ() {
		return z;
	}
	/**
     * Устанавливает значение координаты Z.
     *
     * @param z координата Z.
     */
	public void setZ(int z) {
		this.z = z;
	}
	
	/**
     * Возвращает имя локации.
     *
     * @return имя локации.
     */
	public String getName() {
		return name;
	}
	/**
     * Устанавливает имя локации.
     * Если имя пустое, выбрасывает исключение {@link IllegalArgumentException}.
     *
     * @param name имя локации.
     * @throws IllegalArgumentException если имя пустое.
     */
	public void setName(String name) {
		if(name == "") {
			throw new IllegalArgumentException("У person должно быть имя");
		}
		this.name = name;
	}
     
}
