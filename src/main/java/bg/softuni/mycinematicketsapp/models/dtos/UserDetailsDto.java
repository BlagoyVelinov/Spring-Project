package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.validation.annotation.UniqueEmail;
import bg.softuni.mycinematicketsapp.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsDto {

    private long id;
    
    @NotNull
    @Size(min = 5, max = 30, message = "Username length must be between 5 and 30 characters!")
    @UniqueUsername(message = "This username already in use!")
    private String username;

    @NotNull
    @Size(min = 5, max = 30, message = "Name length must be between 5 and 30 characters!")
    private String name;

    @NotNull(message = "Please select your date of birth.")
    @Past(message = "Date of birth cannot be in the present or future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @NotNull
    @Email(message = "Entered valid email.")
    @UniqueEmail(message = "Email address is already registered!")
    private String email;
    private LocalDateTime created;
    private LocalDateTime modified;
    private String imageUrl;
    private List<UserRole> roles;

    public UserDetailsDto() {
        this.roles = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public UserDetailsDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public long getId() {
        return id;
    }

    public UserDetailsDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserDetailsDto setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public UserDetailsDto setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserDetailsDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public UserDetailsDto setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public UserDetailsDto setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserDetailsDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public UserDetailsDto setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }

}
