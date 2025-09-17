package bg.softuni.mycinematicketsapp.config;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.UserStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class SecurityUserDetails extends org.springframework.security.core.userdetails.User {

    private final Long id;
    private final UserStatus status;

    public SecurityUserDetails(UserEntity user) {
        super(
                user.getUsername(),
                user.getPassword(),
                user.getStatus() == UserStatus.ACTIVE,
                true,
                true,
                user.getStatus() != UserStatus.SUSPENDED,
                user.getRoles()
                        .stream()
                        .map(r -> new SimpleGrantedAuthority(Constant.ROLE_ + r.getRole().name()))
                        .toList()
        );
        this.id = user.getId();
        this.status = user.getStatus();
    }

    public boolean isAdmin() {
        return this.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals(Constant.ROLE_ADMIN));
    }

    public Long getId() {
        return id;
    }

    public UserStatus getStatus() {
        return status;
    }
}

