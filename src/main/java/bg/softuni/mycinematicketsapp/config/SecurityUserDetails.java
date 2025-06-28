package bg.softuni.mycinematicketsapp.config;

import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityUserDetails extends org.springframework.security.core.userdetails.User {

    private final Long id;

    public SecurityUserDetails(UserEntity user) {
        super(
                user.getUsername(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRole().name()))
                        .toList()
        );
        this.id = user.getId();
    }

    public Long getId() {
        return id;
    }
}

