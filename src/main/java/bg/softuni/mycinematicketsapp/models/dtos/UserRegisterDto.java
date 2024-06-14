package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.validation.annotation.FieldMatch;
import bg.softuni.mycinematicketsapp.validation.annotation.UniqueEmail;
import bg.softuni.mycinematicketsapp.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords need to be equals!")
public class UserRegisterDto {

    @NotEmpty
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters.")
    @UniqueUsername(message = "Username already exist!")
    private String username;
    @NotNull
    @Email(message = "Entered valid email.")
    @UniqueEmail(message = "Email address is already registered!")
    private String email;
    @NotEmpty
    @Size(min = 5, max = 20, message = "First name must be between 5 and 20 characters.")
    private String firstName;
    @NotEmpty
    @Size(min = 5, max = 20, message = "Last name must be between 5 and 20 characters.")
    private String lastName;
    @NotEmpty
    @Size(min = 8, max = 20)
    private String password;
    @NotEmpty
    @Size(min = 8, max = 20)
    private String confirmPassword;
    private LocalDateTime created;
    private List<UserRole> roles;

    public UserRegisterDto() {
        this.created = LocalDateTime.now();
        this.roles = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public UserRegisterDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegisterDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserRegisterDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRegisterDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegisterDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public UserRegisterDto setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public UserRegisterDto setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }
}
