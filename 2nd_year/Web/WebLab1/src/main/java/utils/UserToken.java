package utils;

public class UserToken {
    private String token;

    public UserToken(String token) {
        this.token = token;
    }

    public UserToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
