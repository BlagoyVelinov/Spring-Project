package bg.softuni.mycinematicketsapp.models.dtos;

public class UserViewDto {
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public UserViewDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewDto setEmail(String email) {
        this.email = email;
        return this;
    }
}
