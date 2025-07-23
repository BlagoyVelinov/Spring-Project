package bg.softuni.mycinematicketsapp.models.dtos.helpers;

public class LoginResponse {
    long id;
    String username;
    String accessToken;
    private boolean isAdmin;

    public LoginResponse(long id, String username, String accessToken, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.accessToken = accessToken;
        this.isAdmin = isAdmin;
    }

    public long getId() {
        return id;
    }

    public LoginResponse setId(long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoginResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public LoginResponse setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public LoginResponse setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }
}
