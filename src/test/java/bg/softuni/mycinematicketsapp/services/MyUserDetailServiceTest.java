package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.services.session.MyUserDetailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static bg.softuni.mycinematicketsapp.constants.ConstantTest.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailServiceTest {

    private MyUserDetailService serviceToTest;
    @Mock
    private UserRepository mockitoUserRepository;

    @BeforeEach
    void setUp() {
        this.serviceToTest = new MyUserDetailService(this.mockitoUserRepository);
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> this.serviceToTest.loadUserByUsername(TEST_NOT_REGISTERED_USER));
    }

    @Test
    void testUserFoundException() {
        UserEntity testUser = createTestUser();

        when(this.mockitoUserRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        UserDetails userDetails = this.serviceToTest.loadUserByUsername(testUser.getUsername());

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(testUser.getUsername(),
                userDetails.getUsername(), MESSAGE_USERNAME);

        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        Assertions.assertTrue(
                this.containsAuthority(userDetails,
                        ROLE_ + UserRoleEnum.ADMINISTRATOR), MESSAGE_ADMINISTRATOR);
        Assertions.assertTrue(
                this.containsAuthority(userDetails,
                        ROLE_ + UserRoleEnum.USER), MESSAGE_USER);
    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(a -> expectedAuthority.equals(a.getAuthority()));

    }

    private static UserEntity createTestUser() {
        return new UserEntity()
                .setUsername(TEST_USERNAME)
                .setName(TEST_NAME)
                .setActive(false)
                .setPassword(TEST_PASSWORD)
                .setRoles(List.of(
                        new UserRole().setRole(UserRoleEnum.ADMINISTRATOR),
                        new UserRole().setRole(UserRoleEnum.USER)
                ));
    }
}
