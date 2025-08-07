package client.dataValidation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import client.dataStorage.DataForMovie;
import client.executeScript.ExecuteScript;


public class CheckInputFile implements InputChecker {
	
	public static Map<String, Object> data = new HashMap<>();

	CommandParser parseCommand= new CommandParser();
	
	
	@Override
	public Map<String, Object> checkInput() {
		String[] dataMovie =  parseCommand.parseCommandName(ExecuteScript.collectionOfMultipleFiles.get(ExecuteScript.collectionOfMultipleFiles.size() -1).remove(0));
		
		try {
	       	data.put("Name",dataMovie[0]);
	
			if(CheckData.isInteger(dataMovie[1]) ) {
	            int x = Integer.parseInt(dataMovie[1]);
	           	if (x > -754) {
	           		data.put("Coordinates_X", x);
	               } else return null;
	            } else return null;
			
			
			if(CheckData.isLong(dataMovie[2])) {
	            long y = Long.parseLong(dataMovie[2]);
	            data.put("Coordinates_Y", y);
	            
			}
			else return null;
			

			data.put("OscarsCount", Integer.parseInt(dataMovie[3]));
			data.put("TotalBoxOffice", Double.parseDouble(dataMovie[4]));
			data.put("UsaBoxOffice", Double.parseDouble(dataMovie[5]));
			 
			if(CheckData.isInteger(dataMovie[6]) && Integer.parseInt(dataMovie[6])<=DataForMovie.genreNames.length){
				data.put("Genre", DataForMovie.genreNames[Integer.parseInt(dataMovie[6])-1]);
			}
			else if(dataMovie[6] == null || dataMovie[6].isEmpty()) {
				data.put("Genre", null);
			}
			else {
				if(Arrays.asList(DataForMovie.genreNames).contains(dataMovie[6].toUpperCase())) {
					data.put("Genre", dataMovie[6].toUpperCase());
					
				}
				else {
					return null;
				}
			}
			
			
			try {
				data.put("Operator_Name" ,dataMovie[7]);
			}
			catch(IllegalArgumentException e){
				return null;
			}
			
			
			try {
				data.put("Operator_Height", Integer.parseInt(dataMovie[8]));
			}
			catch(IllegalArgumentException e){
				return null;
			}
			
			if(CheckData.isInteger(dataMovie[9]) && Integer.parseInt(dataMovie[9])<=DataForMovie.colorNames.length){
				data.put("Operator_Eye", DataForMovie.colorNames[Integer.parseInt(dataMovie[9])-1]);
			}
			else if(dataMovie[9] == null || dataMovie[9].isEmpty()) {
				data.put("Operator_Eye", null);
			}
			else {
				if(Arrays.asList(DataForMovie.colorNames).contains(dataMovie[9].toUpperCase())) {
					data.put("Operator_Eye", dataMovie[9].toUpperCase());
					
				}
				else {
					return null;
				}
			}
			
			if(CheckData.isInteger(dataMovie[10]) && Integer.parseInt(dataMovie[10])<=DataForMovie.colorNames.length){
				data.put("Operator_Hair", DataForMovie.colorNames[Integer.parseInt(dataMovie[10])-1]);
			}
			else if(dataMovie[9] == null || dataMovie[10].isEmpty()) {
				data.put("Operator_Hair", null);
			}
			else {
				if(Arrays.asList(DataForMovie.colorNames).contains(dataMovie[10].toUpperCase())) {
					data.put("Operator_Hair", dataMovie[10].toUpperCase());
					
				}
				else {
					return null;
				}
			}
			
			if(CheckData.isInteger(dataMovie[11]) && Integer.parseInt(dataMovie[11])<=DataForMovie.countryNames.length){
				data.put("Operator_Nation", DataForMovie.countryNames[Integer.parseInt(dataMovie[11])-1]);
			}
			else if(dataMovie[11] == null || dataMovie[11].isEmpty()) {
				data.put("Operator_Nation", null);
			}
			else {
				if(Arrays.asList(DataForMovie.countryNames).contains(dataMovie[11].toUpperCase())) {
					data.put("Operator_Nation", dataMovie[11].toUpperCase());
					
				}
				else {
					return null;
				}
			}
			
			try {
	            Integer.parseInt(dataMovie[12]);
	            data.put("Location_X", Integer.parseInt(dataMovie[12]));
	        } catch (NumberFormatException e) {
	            return null;
	        }
			
			
			try {
	            Long.parseLong(dataMovie[13]);
	            data.put("Location_Y", Long.parseLong(dataMovie[13]));
	            
	        } catch (NumberFormatException e) {
	        	return null;
	        }
			
			
			try {
	            Integer.parseInt(dataMovie[14]);
	            data.put("Location_Z", Integer.parseInt(dataMovie[14]));
	            
	        } catch (NumberFormatException e) {
	            return null;
	        }
			
			
			if(dataMovie[15] == null|| dataMovie[15].isEmpty()) {
	        	return null;
	        }
	        else {
	        	data.put("Location_Name", dataMovie[15]);
	        	
	        }
			
			
			
			return data;
			

		}
		catch(ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
}
