package client.dataStorage;





public class CurrentMovie {

	private static String id; 
    private static String name; 
    private static String coordinatesId; 
    private static String coordinatesX; 
    private static String coordinatesY; 
    private static String creationDate; 
    private static String oscarsCount; 
    private static String totalBoxOffice;
    private static String usaBoxOffice;
    private static String genre; 
    private static String operatorId; 
    private static String createdBy; 
    private static String operatorName; 
    private static String operatorHeight; 
    private static String operatorEyeColor; 
    private static String operatorHairColor; 
    private static String operatorNationality; 
    private static String LocationId; 
    private static String locationX; 
    private static String locationY; 
    private static String locationZ;
    private static String locationName;
    
	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		CurrentMovie.id = id;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		CurrentMovie.name = name;
	}
	public static String getCoordinatesX() {
		return coordinatesX;
	}
	public static void setCoordinatesX(String coordinatesX) {
		CurrentMovie.coordinatesX = coordinatesX;
	}
	public static String getCoordinatesY() {
		return coordinatesY;
	}
	public static void setCoordinatesY(String coordinatesY) {
		CurrentMovie.coordinatesY = coordinatesY;
	}
	public static String getCreationDate() {
		return creationDate;
	}
	public static void setCreationDate(String creationDate) {
		CurrentMovie.creationDate = creationDate;
	}
	public static String getOscarsCount() {
		return oscarsCount;
	}
	public static void setOscarsCount(String oscarsCount) {
		CurrentMovie.oscarsCount = oscarsCount;
	}
	public static String getTotalBoxOffice() {
		return totalBoxOffice;
	}
	public static void setTotalBoxOffice(String totalBoxOffice) {
		CurrentMovie.totalBoxOffice = totalBoxOffice;
	}
	public static String getUsaBoxOffice() {
		return usaBoxOffice;
	}
	public static void setUsaBoxOffice(String usaBoxOffice) {
		CurrentMovie.usaBoxOffice = usaBoxOffice;
	}
	public static String getGenre() {
		return genre;
	}
	public static void setGenre(String genre) {
		CurrentMovie.genre = genre;
	}
	public static String getCreatedBy() {
		return createdBy;
	}
	public static void setCreatedBy(String createdBy) {
		CurrentMovie.createdBy = createdBy;
	}
	public static String getOperatorName() {
		return operatorName;
	}
	public static void setOperatorName(String operatorName) {
		CurrentMovie.operatorName = operatorName;
	}
	public static String getOperatorHeight() {
		return operatorHeight;
	}
	public static void setOperatorHeight(String operatorHeight) {
		CurrentMovie.operatorHeight = operatorHeight;
	}
	public static String getOperatorEyeColor() {
		return operatorEyeColor;
	}
	public static void setOperatorEyeColor(String operatorEyeColor) {
		CurrentMovie.operatorEyeColor = operatorEyeColor;
	}
	public static String getOperatorHairColor() {
		return operatorHairColor;
	}
	public static void setOperatorHairColor(String operatorHairColor) {
		CurrentMovie.operatorHairColor = operatorHairColor;
	}
	public static String getOperatorNationality() {
		return operatorNationality;
	}
	public static void setOperatorNationality(String operatorNationality) {
		CurrentMovie.operatorNationality = operatorNationality;
	}
	public static String getLocationX() {
		return locationX;
	}
	public static void setLocationX(String locationX) {
		CurrentMovie.locationX = locationX;
	}
	public static String getLocationY() {
		return locationY;
	}
	public static void setLocationY(String locationY) {
		CurrentMovie.locationY = locationY;
	}
	public static String getLocationZ() {
		return locationZ;
	}
	public static void setLocationZ(String locationZ) {
		CurrentMovie.locationZ = locationZ;
	}
	public static String getLocationName() {
		return locationName;
	}
	public static void setLocationName(String locationName) {
		CurrentMovie.locationName = locationName;
	}
	public static String getCoordinatesId() {
		return coordinatesId;
	}
	public static void setCoordinatesId(String coordinatesId) {
		CurrentMovie.coordinatesId = coordinatesId;
	}
	public static String getLocationId() {
		return LocationId;
	}
	public static void setLocationId(String locationId) {
		LocationId = locationId;
	}
	public static String getOperatorId() {
		return operatorId;
	}
	public static void setOperatorId(String operatorId) {
		CurrentMovie.operatorId = operatorId;
	} 
    
	public static String movieToString() {
	    return "id: " + id + "\n" +
	           "name: " + name + "\n" +
	           "coordinatesId: " + coordinatesId + "\n" +
	           "coordinatesX: " + coordinatesX + "\n" +
	           "coordinatesY: " + coordinatesY + "\n" +
	           "creationDate: " + creationDate + "\n" +
	           "oscarsCount: " + oscarsCount + "\n" +
	           "totalBoxOffice: " + totalBoxOffice + "\n" +
	           "usaBoxOffice: " + usaBoxOffice + "\n" +
	           "genre: " + genre + "\n" +
	           "operatorId: " + operatorId + "\n" +
	           "createdBy: " + createdBy + "\n" +
	           "operatorName: " + operatorName + "\n" +
	           "operatorHeight: " + operatorHeight + "\n" +
	           "operatorEyeColor: " + operatorEyeColor + "\n" +
	           "operatorHairColor: " + operatorHairColor + "\n" +
	           "operatorNationality: " + operatorNationality + "\n" +
	           "LocationId: " + LocationId + "\n" +
	           "locationX: " + locationX + "\n" +
	           "locationY: " + locationY + "\n" +
	           "locationZ: " + locationZ + "\n" +
	           "locationName: " + locationName;
	}

    
}

