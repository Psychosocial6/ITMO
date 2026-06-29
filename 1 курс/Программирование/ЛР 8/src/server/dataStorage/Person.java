package server.dataStorage;

import java.io.Serializable;

//import javax.xml.bind.annotation.*;
/**
 * Класс, представляющий персонажа, с аттрибутами, такими как имя, рост, цвет глаз, цвет волос, национальность и местоположение.
 * Этот класс используется для хранения информации о человеке и может быть сериализован в XML.
 */
//@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable{
	private static final long serialVersionUID = 1L; 
	
    private String name; 
    private int height; 
    //@XmlElement(nillable = true)
    private Color eyeColor; 
    //@XmlElement(nillable = true)
    private Color hairColor; 
    //@XmlElement(nillable = true)
    private Country nationality; 
    //@XmlElement(nillable = true)
    private Location location; 
    public Person() {
    	
    }
    
    public Person(String name, int height, Color eyeColor, Color hairColor, Country nationality, Location location) {
    	setName(name);
    	setHeight(height);
    	setEyeColor(eyeColor);
    	setHairColor(hairColor);
    	setNationality(nationality);
    	setLocation(location);
    }
    
    /**
     * Получает имя персонажа.
     * 
     * @return имя персонажа.
     */
 	public String getName() {
		return name;
	}
 	/**
     * Устанавливает имя персонажа.
     * 
     * @param name имя персонажа.
     * @throws IllegalArgumentException если имя не указано или пусто.
     */
	public void setName(String name) {
		if(name == null|| name.isEmpty()) {
			throw new IllegalArgumentException("У person должно быть имя");
		}
		this.name = name;
	}
	
	/**
     * Получает рост персонажа.
     * 
     * @return рост персонажа.
     */
	public int getHeight() {
		return height;
	}
	/**
     * Устанавливает рост персонажа.
     * 
     * @param height рост персонажа.
     * @throws IllegalArgumentException если рост меньше или равен нулю.
     */
	public void setHeight(int height) {
		if (height<=0) {
			throw new IllegalArgumentException("Рост не может быть отрицательным!");
		}
		else {
			this.height = height;
		}
	}
	
	/**
     * Получает цвет глаз персонажа.
     * 
     * @return цвет глаз.
     */
	public Color getEyeColor() {
		return eyeColor;
	}
	/**
     * Устанавливает цвет глаз персонажа.
     * 
     * @param eyeColor строковое представление цвета глаз.
     * @throws IllegalArgumentException если введен некорректный цвет.
     */
	public void setEyeColor(Color eyeColor) {
		this.eyeColor = eyeColor;
	}
	
	/**
     * Получает цвет волос персонажа.
     * 
     * @return цвет волос.
     */
	public Color getHairColor() {
		return hairColor;
	}
	/**
     * Устанавливает цвет волос персонажа.
     * 
     * @param hairColor2 строковое представление цвета волос.
     * @throws IllegalArgumentException если введен некорректный цвет.
     */
	public void setHairColor(Color hairColor) {
		this.hairColor = hairColor;
	}
	
	/**
     * Получает национальность персонажа.
     * 
     * @return национальность.
     */
	public Country getNationality() {
		return nationality;
	}
	/**
     * Устанавливает национальность персонажа.
     * 
     * @param nationality строковое представление страны.
     * @throws IllegalArgumentException если введена некорректная страна.
     */
	public void setNationality(Country nationality) {
		this.nationality = nationality;

		
	}
	
	/**
     * Получает местоположение персонажа.
     * 
     * @return местоположение персонажа.
     */
	public Location getLocation() {
		return location;
	}
	/**
     * Устанавливает местоположение персонажа.
     * 
     */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
     * Возвращает строковое представление локации оператора.
     * 
     */
	@Override
	public String toString() {
		try {
		return "X= " + location.getX() + " , " + "Y= " + location.getY() + " , " +"Z= " + location.getZ() + " , " + "Name= " + location.getName();
		}
		catch(NullPointerException e) {
			return "";
		}
		
		}
	
	
}
