package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.validation.annotation.FieldMatch;
import bg.softuni.mycinematicketsapp.validation.annotation.UniqueEmail;
import bg.softuni.mycinematicketsapp.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@FieldMatch(first = "password", second = "confirmPassword", message = "Passwords need to be equals!")
public class UserRegisterDto {

    @NotNull
    @Size(min = 5, max = 30, message = "Username length must be between 5 and 30 characters!")
    @UniqueUsername(message = "Username already exist!")
    private String username;
    @NotNull
    @Email(message = "Entered valid email.")
    @UniqueEmail(message = "Email address is already registered!")
    private String email;
    @NotNull
    @Size(min = 5, max = 30, message = "Name length must be between 5 and 30 characters!")
    private String name;
    @NotNull(message = "Please select your date of birth.")
    @Past(message = "Date of birth cannot be in the present or future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    @NotEmpty
    @Size(min = 8, max = 20, message = "Password length must be between 8 and 20 symbols!")
    private String password;

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

    public String getName() {
        return name;
    }

    public UserRegisterDto setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public UserRegisterDto setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
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
