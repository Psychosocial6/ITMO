package exceptions;

public class AmmoException extends Exception {
    private String message;

    public AmmoException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
