package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.constants.ExceptionMessages;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.services.TicketService;
import bg.softuni.mycinematicketsapp.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceImplTest {
    @Mock
    private UserService userService;

    @Mock
    private TicketService ticketService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private SecurityServiceImpl securityService;

    private UserEntity adminUser;
    private UserEntity normalUser;

    @BeforeEach
    void setUp() {
        adminUser = new UserEntity()
                .setUsername(ConstantTest.ADMIN_USERNAME)
                .setRoles(List.of(new UserRole(UserRoleEnum.ADMINISTRATOR), new UserRole(UserRoleEnum.USER)));
        adminUser.setId(1L);

        normalUser = new UserEntity()
                .setUsername(ConstantTest.TEST_USERNAME)
                .setRoles(List.of(new UserRole(UserRoleEnum.USER)));
        normalUser.setId(2L);
    }

    @Test
    void validateCurrentUser_AsAdmin_ShouldPass() {
        when(authentication.getName()).thenReturn(ConstantTest.ADMIN_USERNAME);
        when(userService.getUserByUsername(ConstantTest.ADMIN_USERNAME)).thenReturn(adminUser);

        assertDoesNotThrow(() -> securityService.validateCurrentUser(99L, authentication));
    }

    @Test
    void validateCurrentUser_AsOwner_ShouldPass() {
        when(authentication.getName()).thenReturn(ConstantTest.TEST_USERNAME);
        when(userService.getUserByUsername(ConstantTest.TEST_USERNAME)).thenReturn(normalUser);

        assertDoesNotThrow(() -> securityService.validateCurrentUser(2L, authentication));
    }

    @Test
    void validateCurrentUser_AsOtherUser_ThrowForbidden() {
        when(authentication.getName()).thenReturn(ConstantTest.TEST_USERNAME);
        when(userService.getUserByUsername(ConstantTest.TEST_USERNAME)).thenReturn(normalUser);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> securityService.validateCurrentUser(1L, authentication));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
        assertEquals(ExceptionMessages.NOT_ADMIN_RIGHTS, exception.getReason());
    }

    @Test
    void validateUserForTicket_AsAdmin_ShouldPass() {
        when(authentication.getName()).thenReturn(ConstantTest.ADMIN_USERNAME);
        when(userService.getUserByUsername(ConstantTest.ADMIN_USERNAME)).thenReturn(adminUser);

        assertDoesNotThrow(() -> securityService.validateUserForTicket(5L, authentication));
    }
}
