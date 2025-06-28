package bg.softuni.mycinematicketsapp.models.dtos.helpers;

public class LoginResponse {
    long id;
    String username;
    String password;

    public LoginResponse(long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public LoginResponse setPassword(String password) {
        this.password = password;
        return this;
    }
}
