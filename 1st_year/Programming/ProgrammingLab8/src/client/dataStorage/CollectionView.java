package client.dataStorage;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class CollectionView {

	static private Map<Long, String> movieView = new LinkedHashMap<>();
	static private Map<Integer, Long> movieCoordinates = new LinkedHashMap<>();
	static private ArrayList<String> createdBy = new ArrayList<>();
	
	public CollectionView(LinkedHashMap<Long, String> movieView, LinkedHashMap<Integer, Long> movieCoordinates, ArrayList<String> createdBy) {
		setMovieView(movieView);
		setMovieCoordinates(movieCoordinates);
		setCreatedBy(createdBy);
	}

	static public Map<Long, String> getMovieView() {
		return movieView;
	}

	static public void setMovieView(LinkedHashMap<Long, String> movieView) {
		CollectionView.movieView = movieView;
	}

	static public Long getElement(int id) {
		ArrayList<Long> keys = new ArrayList<>(movieView.keySet());
	    if (id >= 0 && id < keys.size()) {
	        return keys.get(id);
	    } else {
	        System.out.println("Ошибка: индекс " + id + " вне границ списка ключей. Размер = " + keys.size());
	        return null; // или выбрось исключение, если это критично
	    }
		
	}

	public static Map<Integer, Long> getMovieCoordinates() {
		return movieCoordinates;
	}

	public static void setMovieCoordinates(Map<Integer, Long> movieCoordinates) {
		CollectionView.movieCoordinates = movieCoordinates;
	}

	public static ArrayList<String> getCreatedBy() {
		return createdBy;
	}

	public static void setCreatedBy(ArrayList<String> createdBy) {
		CollectionView.createdBy = createdBy;
	}
}
