package exceptions;

import boats.Boat;

public class CapacityException extends Exception {
    private String boat;

    public CapacityException(String boat) {
        this.boat = boat;
    }

    @Override
    public String getMessage() {
        return String.format("Превышено ограничение вместимости на: %s\n", boat);
    }
}
