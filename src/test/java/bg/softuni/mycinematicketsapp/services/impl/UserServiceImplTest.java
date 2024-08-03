package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.repository.UserRoleRepository;
import bg.softuni.mycinematicketsapp.services.UserRoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private static final String ADMIN_WITH_USERNAME = "admin";
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String USER_WITH_USERNAME = "firstLast";
    private static final String USER_EMAIL = "firstLast@gmail.com";
    private static final String NOT_FOUND_USERNAME = "notFound";


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
    @Mock
    private UserRoleRepository mockRoleRepository;

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
        UserRegisterDto testUser = getUserRegisterDto(USER_WITH_USERNAME, USER_EMAIL);

        this.registerUser(testUser, UserRoleEnum.USER);

        verify(this.mockUserRepository).save(this.userEntityCaptor.capture());

        UserEntity actualSaveEntity = this.userEntityCaptor.getValue();

        Assertions.assertNotNull(actualSaveEntity);
        Assertions.assertEquals(testUser.getName(), actualSaveEntity.getName());
        Assertions.assertEquals(testUser.getEmail(), actualSaveEntity.getEmail());
        Assertions.assertEquals(testUser.getName(), actualSaveEntity.getName());
        Assertions.assertEquals(testUser.getPassword() + testUser.getPassword(),
                actualSaveEntity.getPassword());

    }

    @Test
    void testUserIsNotAdmin() {

        UserRegisterDto testUser = getUserRegisterDto(USER_WITH_USERNAME, USER_EMAIL);

        this.registerUser(testUser, UserRoleEnum.USER);

        verify(this.mockUserRepository).save(this.userEntityCaptor.capture());

        UserEntity actualSaveEntity = this.userEntityCaptor.getValue();
        Mockito.when(this.mockUserRepository.findByUsername(USER_WITH_USERNAME)).thenReturn(Optional.of(actualSaveEntity));

        actualSaveEntity.setRoles(testUser.getRoles());

        Optional<UserEntity> byUsername = this.mockUserRepository.findByUsername(actualSaveEntity.getUsername());

        assertTrue(byUsername.isPresent());
        Assertions.assertEquals(testUser.getUsername(), byUsername.get().getUsername());
        Assertions.assertFalse(this.userServiceTest.isAdmin(actualSaveEntity.getUsername()));
    }
    @Test
    void testUserIsAdmin() {

        UserRegisterDto testUser = getUserRegisterDto(ADMIN_WITH_USERNAME, ADMIN_EMAIL);

        this.registerUser(testUser, UserRoleEnum.ADMINISTRATOR);

        verify(this.mockUserRepository).save(this.userEntityCaptor.capture());

        UserEntity actualSaveEntity = this.userEntityCaptor.getValue();
        Mockito.when(this.mockUserRepository.findByUsername(ADMIN_WITH_USERNAME)).thenReturn(Optional.of(actualSaveEntity));

        actualSaveEntity.setRoles(testUser.getRoles());

        Optional<UserEntity> byUsername = this.mockUserRepository.findByUsername(actualSaveEntity.getUsername());

        assertTrue(byUsername.isPresent());
        Assertions.assertEquals(testUser.getUsername(), byUsername.get().getUsername());
        Assertions.assertTrue(this.userServiceTest.isAdmin(actualSaveEntity.getUsername()));
    }



    @Test
    void testGetUserByUsername() {

        UserRegisterDto testUser = this.getUserRegisterDto(USER_WITH_USERNAME, USER_EMAIL);

        this.registerUser(testUser, UserRoleEnum.USER);

        verify(this.mockUserRepository).save(this.userEntityCaptor.capture());

        UserEntity actualSaveEntity = this.userEntityCaptor.getValue();
        Mockito.when(mockUserRepository.findByUsername(USER_WITH_USERNAME)).thenReturn(Optional.of(actualSaveEntity));

        Assertions.assertEquals(this.userServiceTest.getUserByUsername(USER_WITH_USERNAME), actualSaveEntity);
    }

    @Test
    void testGetUserByUsernameNotFound() {

        UserRegisterDto testUser = this.getUserRegisterDto(USER_WITH_USERNAME, USER_EMAIL);

        this.registerUser(testUser, UserRoleEnum.USER);

        verify(this.mockUserRepository).save(this.userEntityCaptor.capture());

        UserEntity actualSaveEntity = this.userEntityCaptor.getValue();
        Mockito.when(this.mockUserRepository.findByUsername(NOT_FOUND_USERNAME)).thenReturn(Optional.of(actualSaveEntity));

        Optional<UserEntity> byUsername = this.mockUserRepository.findByUsername(actualSaveEntity.getUsername());
        assertTrue(byUsername.isEmpty());

    }





    private void registerUser(UserRegisterDto testUser, UserRoleEnum roleEnum) {
        UserRole userRole = new UserRole(roleEnum);
        testUser.setRoles(List.of(userRole));

        when(this.mockPasswordEncoder.encode(testUser.getPassword()))
                .thenReturn(testUser.getPassword() + testUser.getPassword());

        this.userServiceTest.registerUser(testUser);
    }

    private UserRegisterDto getUserRegisterDto(String username, String email) {
        UserRegisterDto testUser = this.createUser(username, email);

        List<UserRoleEnum> rolesEnums = testUser.getRoles().stream().map(UserRole::getRole).toList();
        Mockito.when(this.mockRoleRepository.findAllByRoleIn(rolesEnums)).thenReturn(testUser.getRoles());
        return testUser;
    }

    private UserRegisterDto createUser(String username, String email) {
        this.initUserRoleRepository();
        List<UserRole> roles = Arrays.stream(UserRoleEnum.values())
                .map(UserRole::new)
                .toList();


        return new UserRegisterDto()
                .setUsername(username)
                .setEmail(email)
                .setName("fullName")
                .setPassword("123456789")
                .setRoles(roles);
    }

    private void initUserRoleRepository(){
        if (this.mockRoleRepository.count() == 0) {
            List<UserRole> roles = Arrays.stream(UserRoleEnum.values())
                    .map(UserRole::new)
                    .toList();

            this.mockRoleRepository.saveAll(roles);
        }
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(this.mockUserRepository);
        Mockito.reset(this.mockRoleRepository);
    }
}
