package client.dataStorage;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import client.ClientResponseReceiver;

public class DataForMovie {
	
	public static Map<String, Boolean> additionalInput = new LinkedHashMap<>();
    
    public static String[] colorNames;
    public static String[] countryNames;
    public static String[] genreNames;
    
    @SuppressWarnings("unchecked")
	public DataForMovie(ClientResponseReceiver receiver) throws ClassNotFoundException, IOException {
    	
		additionalInput = (Map<String, Boolean>) receiver.getData();
		Map<String, String[]> enumData = (Map<String, String[]>) receiver.getData();
        colorNames = enumData.get("colors");
        countryNames = enumData.get("countries");
        genreNames = enumData.get("genres");
    }
}
