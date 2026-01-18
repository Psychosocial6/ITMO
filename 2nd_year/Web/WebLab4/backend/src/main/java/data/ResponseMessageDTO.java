package data;

public class ResponseMessageDTO {
    private String message;
    private String token;
    private String login;

    public ResponseMessageDTO() {}

    public ResponseMessageDTO(String message) {
        this.message = message;
    }

    public ResponseMessageDTO(String message, String token, String login) {
        this.message = message;
        this.token = token;
        this.login = login;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
}