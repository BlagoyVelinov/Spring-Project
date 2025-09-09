package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.UserStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String username;
    @Column(name ="name", nullable = false)
    private String name;
    @Column(name ="birthdate", nullable = false)
    private LocalDate birthdate;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;
    @Column
    private LocalDateTime created;
    @Column
    private LocalDateTime modified;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "activation_token")
    private String activationToken;
    @OneToMany
    private List<Ticket> tickets;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRole> roles;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    public UserEntity() {
        this.tickets = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.orders = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public UserEntity setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserStatus getStatus() {
        return status;
    }

    public UserEntity setStatus(UserStatus status) {
        this.status = status;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public UserEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public UserEntity setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public UserEntity setActivationToken(String activationToken) {
        this.activationToken = activationToken;
        return this;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public UserEntity setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public UserEntity setRoles(List<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public UserEntity setOrders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }
}
