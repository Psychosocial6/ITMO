package exceptions;

public class RequestParsingException extends RuntimeException {
    private String message;

    public RequestParsingException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
