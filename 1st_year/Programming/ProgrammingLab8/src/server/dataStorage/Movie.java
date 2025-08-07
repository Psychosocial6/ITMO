package server.dataStorage;


//import javax.xml.bind.annotation.*;
//import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.io.Serializable;
import java.time.ZonedDateTime;
/**
 * Класс, представляющий фильм с его характеристиками, такими как название, координаты, количество Оскаров, кассовые сборы и другие.
 * Этот класс используется для сериализации и десериализации в формате XML.
 * 
 * 
 * 
 */
//@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class Movie implements Comparable<Movie>, Serializable{
	private static final long serialVersionUID = 1L;

	private String createdBy; // логин пользователя
	
	
	private long id; 
    private String name; 
    private Coordinates coordinates; 
    //@XmlJavaTypeAdapter(ZonedDateTimeAdapter.class)
    private java.time.ZonedDateTime creationDate; 
    private int oscarsCount; 
    private double totalBoxOffice;
    private Double usaBoxOffice;
    //@XmlElement(nillable = true)
    private MovieGenre genre;
    //@XmlElement(nillable = true)
    private Person operator; 
    
    /**
     * Конструктор без параметров.
     * Используется для сериализации и десериализации.
     */
    public Movie() {
    	
    }
    /**
     * Конструктор с параметрами для создания нового объекта фильма.
     * 
     * @param id Идентификатор фильма.
     * @param name Название фильма.
     * @param coordinates2 Координаты фильма.
     * @param creationDate Дата создания фильма.
     * @param oscarsCount Количество Оскаров.
     * @param totalBoxOffice Общие кассовые сборы.
     * @param usaBoxOffice Кассовые сборы в США.
     * @param movieGenre Жанр фильма.
     * @param person Оператор фильма.
     */
    public Movie(long id, String name, Coordinates coordinates, ZonedDateTime creationDate, int oscarsCount, double totalBoxOffice,
    		Double usaBoxOffice, MovieGenre movieGenre, Person person, String login){
    	this.setId(id);
    	this.setName(name);
    	this.setCoordinates(coordinates);
    	this.setCreationDate(creationDate);
    	this.setOscarsCount(oscarsCount);
    	this.setTotalBoxOffice(totalBoxOffice);
    	this.setUsaBoxOffice(usaBoxOffice);
    	this.setGenre(movieGenre);
    	this.setOperator(person);
    	this.setCreatedBy(login);
    }
    
    
    /**
     * Сравнение фильмов по общим кассовым сборам.
     * 
     * @param other Другой фильм для сравнения.
     * @return Результат сравнения кассовых сборов двух фильмов.
     */
    @Override
    public int compareTo(Movie other) {
         return Double.compare(this.totalBoxOffice, other.totalBoxOffice); 
    }
    /*
    public void setCreatedBy(String username) {
        this.createdBy = username;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    */
    /**
     * Возвращает идентификатор фильма.
     * 
     * @return Идентификатор фильма.
     */
    public long getId() {
    	return id;
    }
    /**
     * Устанавливает идентификатор фильма.
     * 
     * @param id Идентификатор фильма.
     */
	public void setId(long id) {
		this.id = id;
	}
	/**
     * Возвращает название фильма.
     * 
     * @return Название фильма.
     */
	public String getName() {
    	return name;
    }
	/**
     * Устанавливает название фильма.
     * 
     * @param name Название фильма.
     */
	public void setName(String name) {
		this.name = name;
	}
	/**
     * Возвращает координаты фильма.
     * 
     * @return Координаты фильма.
     */
	public Coordinates getCoordinates() {
		return coordinates;
	}
	/**
     * Устанавливает координаты фильма.
     * 
     * @param coordinates Координаты фильма.
     */
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	/**
     * Возвращает дату создания фильма.
     * 
     * @return Дата создания фильма.
     */
	public java.time.ZonedDateTime getCreationDate() {
		return creationDate;
	}
	/**
     * Устанавливает дату создания фильма.
     * 
     * @param creationDate Дата создания фильма.
     */
	public void setCreationDate(java.time.ZonedDateTime creationDate) {
		this.creationDate = creationDate;
	}
	/**
     * Возвращает количество Оскаров у фильма.
     * 
     * @return Количество Оскаров.
     */
	public int getOscarsCount() {
		return oscarsCount;
	}
	/**
     * Устанавливает количество Оскаров у фильма.
     * 
     * @param oscarsCount Количество Оскаров.
     */
	public void setOscarsCount(int oscarsCount) {
		this.oscarsCount = oscarsCount;
	}
	/**
     * Возвращает общие кассовые сборы фильма.
     * 
     * @return Общие кассовые сборы фильма.
     */
	public double getTotalBoxOffice() {
		return totalBoxOffice;
	}
	/**
     * Устанавливает общие кассовые сборы фильма.
     * 
     * @param totalBoxOffice Общие кассовые сборы фильма.
     */
	public void setTotalBoxOffice(double totalBoxOffice) {
		this.totalBoxOffice = totalBoxOffice;
	}
	/**
     * Возвращает кассовые сборы фильма в США.
     * 
     * @return Кассовые сборы фильма в США.
     */
	public Double getUsaBoxOffice() {
		return usaBoxOffice;
	}
	/**
     * Устанавливает кассовые сборы фильма в США.
     * 
     * @param usaBoxOffice Кассовые сборы фильма в США.
     */
	public void setUsaBoxOffice(Double usaBoxOffice) {
		this.usaBoxOffice = usaBoxOffice;
	}
	/**
     * Возвращает жанр фильма.
     * 
     * @return Жанр фильма.
     */
	public MovieGenre getGenre() {
		return genre;
	}
	/**
     * Устанавливает жанр фильма. Жанр должен быть из перечисления {@link MovieGenre}.
     * 
     * @param genre Жанр фильма.
     * @throws IllegalArgumentException если жанр некорректен.
     */
	public void setGenre(MovieGenre genre) {
		boolean hit = false;
		for (MovieGenre genr: MovieGenre.values()) {
			if (genr == genre) {
				hit = true;
			}
		}
		if(hit == true || genre == null  ) {
			this.genre = genre;
		}
		else throw new IllegalArgumentException("Введена некорректная жанр");

		

	}
	/**
     * Возвращает оператора фильма.
     * 
     * @return Оператор фильма.
     */
	public Person getOperator() {
		return operator;
	}
	/**
     * Устанавливает оператора фильма.
     * 
     * @param operator Оператор фильма.
     */
	public void setOperator(Person operator) {
		this.operator = operator;
	}
	/**
     * Представление оператора фильма в строковом виде.
     * 
     * @return Строковое представление оператора.
     */
	public String operatorToString() {
		try {
			return "name = " + operator.getName() + ", height = " + operator.getHeight() + ", EyeColor = " + operator.getEyeColor() +  ", HairColor = " + operator.getHairColor() +   ", Nationality = " + operator.getNationality() ;
		}
		catch(NullPointerException e){
			return "";
		}
		
	}
	/**
     * Получает строковое представление местоположения оператора.
     * 
     * @return Строковое представление местоположения.
     */
	public String getLocationToString() {
		try {
			return operator.toString();
		}
		catch(NullPointerException e){
			return "";
		}
	}
	/**
     * Представление фильма в строковом виде.
     * 
     * @return Строковое представление фильма.
     */
	@Override
    public String toString() {
		return "Movie{" +
                "  "+ "id = " + id  + "\n" + 
                "  "+ "name = " + name +  "\n" + 
                "  "+ "coordinates : "  + "\n" + 
                "    "+ "X = " + coordinates.getX() +  ", Y = "+ coordinates.getY()  + "\n" +
                "  "+ "creationDate = " + creationDate  + "\n" + 
                "  "+ "oscarsCount = " + oscarsCount  + "\n" +
                "  "+ "totalBoxOffice = " + totalBoxOffice  + "\n" + 
                "  "+ "usaBoxOffice = " + usaBoxOffice  + "\n" + 
                "  "+ "genre = " + genre  + "\n" + 
                "  "+ "created by = " + createdBy + "\n"+
                "  "+ "operator : " + "\n" + 
                "    "+ operatorToString()  +   ", Location : " + "\n" + 
                "    " + getLocationToString() +
                '}'; 
    	
    }

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
