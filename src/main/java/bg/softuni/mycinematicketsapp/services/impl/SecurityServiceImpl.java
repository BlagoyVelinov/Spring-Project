package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ExceptionMessages;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.services.SecurityService;
import bg.softuni.mycinematicketsapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;

    public SecurityServiceImpl(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void validateCurrentUser(long userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        UserEntity currentUser = userService.getUserByUsername(currentUsername);

        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(userRole -> userRole
                        .getRole()
                        .name()
                        .equals(UserRoleEnum.ADMINISTRATOR.name()));

        if (!isAdmin && currentUser.getId() != userId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ExceptionMessages.NOT_ADMIN_RIGHTS);
        }
    }
}
