package bg.softuni.mycinematicketsapp.models.dtos.view;

public class UserViewDto {
    private long id;
    private String name;
    private String username;
    private String email;
    private String imageUrl;
    private boolean isAdmin;

    public long getId() {
        return id;
    }

    public UserViewDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserViewDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserViewDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public UserViewDto setAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }
}
