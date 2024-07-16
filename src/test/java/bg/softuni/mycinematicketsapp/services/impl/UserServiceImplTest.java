package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.services.UserRoleService;
import bg.softuni.mycinematicketsapp.services.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {


    private UserServiceImpl userServiceTest;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private UserRoleService mockUserRoleService;
    @Mock
    private ApplicationEventPublisher mockAppEventPublisher;

    @BeforeEach
    void setUp() {
        this.userServiceTest = new UserServiceImpl(
                this.mockUserRepository,
                this.mockUserRoleService,
                this.mockPasswordEncoder,
                new ModelMapper(),
                this.mockAppEventPublisher);
    }

    @Test
    void testUserRegistration() {
        UserRegisterDto testUser = this.createTestUser();

        when(this.mockPasswordEncoder.encode(testUser.getPassword()))
                .thenReturn(testUser.getPassword() + testUser.getPassword());

        this.userServiceTest.registerUser(testUser);

        verify(this.mockUserRepository).save(this.userEntityCaptor.capture());

        UserEntity actualSaveEntity = this.userEntityCaptor.getValue();

        Assertions.assertNotNull(actualSaveEntity);
        Assertions.assertEquals(testUser.getName(), actualSaveEntity.getName());
        Assertions.assertEquals(testUser.getEmail(), actualSaveEntity.getEmail());
        Assertions.assertEquals(testUser.getName(), actualSaveEntity.getName());
        Assertions.assertEquals(testUser.getPassword() + testUser.getPassword(),
                actualSaveEntity.getPassword());

    }

    private UserRegisterDto createTestUser() {
        return new UserRegisterDto()
                .setUsername("firstLast")
                .setEmail("firstLast@example.com")
                .setName("fullName")
                .setPassword("123456789")
                .setRoles(List.of(
                        new UserRole().setRole(UserRoleEnum.ADMINISTRATOR),
                        new UserRole().setRole(UserRoleEnum.USER)
                ));
    }

}
