package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.entities.UserRole;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDetailsDto {

    private long id;
    private String username;
    private String name;
    private LocalDate birthdate;
    private String email;
    private boolean isActive;
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

    public boolean isActive() {
        return isActive;
    }

    public UserDetailsDto setActive(boolean active) {
        isActive = active;
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
