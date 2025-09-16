package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ExceptionMessages;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.services.SecurityService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import bg.softuni.mycinematicketsapp.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;
    private final TicketService ticketService;

    public SecurityServiceImpl(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
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

    @Transactional
    @Override
    public void validateUserForTicket(long ticketId, Authentication authentication) {
        String username = authentication.getName();
        UserEntity currentUser = userService.getUserByUsername(username);

        boolean isAdmin = currentUser.getRoles()
                .stream()
                .anyMatch(userRole -> userRole
                        .getRole()
                        .name()
                        .equals(UserRoleEnum.ADMINISTRATOR.name()));

        if (!isAdmin) {
            ticketService.getAllTicketsByUser(currentUser.getId()).stream()
                    .filter(t -> t.getId() == ticketId)
                    .findFirst()
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.FORBIDDEN, ExceptionMessages.TICKET_ACCESS_DENIED
                    ));
        }
    }
}
