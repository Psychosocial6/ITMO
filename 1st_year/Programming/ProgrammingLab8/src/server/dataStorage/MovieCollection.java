package server.dataStorage;



import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс, представляющий коллекцию фильмов. 
 * Он предоставляет функциональность для хранения фильмов в виде множества, 
 * а также методы для сортировки и получения даты инициализации коллекции.
 */
public class MovieCollection {
	
	public static Set<Movie> movies = Collections.synchronizedSet(new HashSet<>());
	
	private static final LocalDateTime initializationDate = LocalDateTime.now();
	
	private static Instant lastUpdate = Instant.now();

	public MovieCollection(HashSet<Movie> movies) {
		MovieCollection.movies = movies;
	}
	/**
     * Сортирует коллекцию фильмов по общим кассовым сборам в порядке возрастания.
     * 
     * @return Отсортированный список фильмов.
     */
	public static ArrayList<Movie> sortedMovie(){
		ArrayList<Movie> sortedList = new ArrayList<>(movies);
		Collections.sort(sortedList);
		
		return sortedList;
	}
	
	
	public static LocalDateTime getInitializationdate() {
		return initializationDate;
	}
	
	
	public static Instant getLastUpdate() {
		return lastUpdate;
	}
	public static void setLastUpdate(Instant lastUpdate) {
		MovieCollection.lastUpdate = lastUpdate;
	}
  
	
}
