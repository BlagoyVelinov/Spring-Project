package bg.softuni.mycinematicketsapp.services;

import org.springframework.security.core.Authentication;

public interface SecurityService {

    void validateCurrentUser(long userId, Authentication authentication);
}
