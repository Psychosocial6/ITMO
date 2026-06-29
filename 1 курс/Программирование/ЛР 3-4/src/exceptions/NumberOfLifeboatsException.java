package exceptions;

public class NumberOfLifeboatsException extends Exception {
    private String boat;

    public NumberOfLifeboatsException(String boat) {
        this.boat = boat;
    }

    @Override
    public String getMessage() {
        return String.format("Шлюпки кончились на %s\n", boat);
    }
}
