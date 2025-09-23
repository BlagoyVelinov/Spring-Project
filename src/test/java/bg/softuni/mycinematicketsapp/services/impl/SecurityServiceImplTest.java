package bg.softuni.mycinematicketsapp.services.impl;

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
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
                .setUsername("admin")
                .setRoles(List.of(new UserRole(UserRoleEnum.ADMINISTRATOR), new UserRole(UserRoleEnum.USER)));
        adminUser.setId(1L);

        normalUser = new UserEntity()
                .setUsername("user")
                .setRoles(List.of(new UserRole(UserRoleEnum.USER)));
        normalUser.setId(2L);
    }

    @Test
    void validateCurrentUser_AsAdmin_ShouldPass() {
        when(authentication.getName()).thenReturn("admin");
        when(userService.getUserByUsername("admin")).thenReturn(adminUser);

        assertDoesNotThrow(() -> securityService.validateCurrentUser(99L, authentication));
    }

}
