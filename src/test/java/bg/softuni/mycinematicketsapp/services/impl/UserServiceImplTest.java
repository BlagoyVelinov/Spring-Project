package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.models.dtos.UserDetailsDto;
import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.models.enums.UserStatus;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.repository.UserRoleRepository;
import bg.softuni.mycinematicketsapp.services.UserRoleService;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private ApplicationEventPublisher appEventPublisher;

    @Mock
    private UserRoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        this.userService = new UserServiceImpl(
                userRepository,
                userRoleService,
                passwordEncoder,
                new ModelMapper(),
                appEventPublisher
        );
    }

    @Test
    void testUserRegistrationSavesCorrectEntity() {
        UserRegisterDto testUser = getUserRegisterDto(ConstantTest.TEST_USERNAME, ConstantTest.USER_EMAIL);
        registerUser(testUser);

        verify(userRepository).save(userEntityCaptor.capture());
        UserEntity savedEntity = userEntityCaptor.getValue();

        Assertions.assertNotNull(savedEntity);
        assertEquals(testUser.getName(), savedEntity.getName());
        assertEquals(testUser.getEmail(), savedEntity.getEmail());
        assertEquals(testUser.getUsername(), savedEntity.getUsername());
        assertEquals(testUser.getPassword() + testUser.getPassword(), savedEntity.getPassword());
    }

    @Test
    void testUserIsNotAdmin() {
        UserRegisterDto testUser = getUserRegisterDto(ConstantTest.TEST_USERNAME, ConstantTest.USER_EMAIL);
        registerUser(testUser);

        verify(userRepository).save(userEntityCaptor.capture());
        UserEntity savedEntity = userEntityCaptor.getValue();

        when(userRepository.findByUsername(ConstantTest.TEST_USERNAME)).thenReturn(Optional.of(savedEntity));

        assertFalse(userService.isAdmin(ConstantTest.TEST_USERNAME));
    }

    @Test
    void testUserIsAdmin() {
        UserEntity adminEntity = getAdmin();;

        Mockito.when(userRepository.findByUsername(ConstantTest.ADMIN_USERNAME))
                .thenReturn(Optional.of(adminEntity));

        assertTrue(userService.isAdmin(ConstantTest.ADMIN_USERNAME));
    }

    @Test
    void testGetUserByUsername_Found() {
        UserRegisterDto testUser = getUserRegisterDto(ConstantTest.TEST_USERNAME, ConstantTest.USER_EMAIL);
        registerUser(testUser);

        verify(userRepository).save(userEntityCaptor.capture());
        UserEntity savedEntity = userEntityCaptor.getValue();

        when(userRepository.findByUsername(ConstantTest.TEST_USERNAME)).thenReturn(Optional.of(savedEntity));

        assertEquals(savedEntity, userService.getUserByUsername(ConstantTest.TEST_USERNAME));
    }

    @Test
    void testGetUserByUsername_NotFoundThrows() {
        when(userRepository.findByUsername("ghost")).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> userService.getUserByUsername("ghost"));
    }

    @Test
    void testGetUserDtoByUsername_NonAdmin() {
        UserEntity user = getUserEntity();

        Mockito.when(userRepository.findByUsername(ConstantTest.TEST_USERNAME))
                .thenReturn(Optional.of(user));

        UserViewDto dto = userService.getUserDtoByUsername(ConstantTest.TEST_USERNAME);

        Assertions.assertNotNull(dto);
        assertEquals(ConstantTest.TEST_USERNAME, dto.getUsername());
        assertFalse(dto.isAdmin());
    }

    @Test
    void testGetUserDtoByUsername_Admin() {
        UserEntity admin = getAdmin();

        Mockito.when(userRepository.findByUsername(ConstantTest.ADMIN_USERNAME))
                .thenReturn(Optional.of(admin));

        UserViewDto dto = userService.getUserDtoByUsername(ConstantTest.ADMIN_USERNAME);

        Assertions.assertNotNull(dto);
        assertEquals(ConstantTest.ADMIN_USERNAME, dto.getUsername());
        assertTrue(dto.isAdmin());
    }

    @Test
    void testInitAdminUserInDb_WhenDbIsEmpty_ShouldSaveAdmin() {

        Mockito.when(userRepository.count()).thenReturn(0L);

        List<UserRole> roles = List.of(new UserRole(UserRoleEnum.USER), new UserRole(UserRoleEnum.ADMINISTRATOR));
        Mockito.when(userRoleService.getAllRoles()).thenReturn(roles);

        userService.initAdminUserInDb();

        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        Mockito.verify(userRepository).save(captor.capture());

        UserEntity savedUser = captor.getValue();
        assertEquals(Constant.USER_USERNAME, savedUser.getUsername());
        assertEquals(Constant.USER_EMAIL, savedUser.getEmail());
        assertEquals(UserStatus.ACTIVE, savedUser.getStatus());
        assertEquals(roles, savedUser.getRoles());
    }

    @Test
    void testInitAdminUserInDb_WhenDbNotEmpty_ShouldNotSaveAdmin() {
        Mockito.when(userRepository.count()).thenReturn(1L);

        userService.initAdminUserInDb();

        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any(UserEntity.class));
    }

    @Test
    void testGetUserDetailsDtoById() {
        UserEntity user = getUserEntityById();

        UserDetailsDto userDetails = userService.getUserDetailsDtoById(user.getId());

        Assertions.assertNotNull(userDetails);
        assertEquals(ConstantTest.TEST_USERNAME, userDetails.getUsername());
    }

    @Test
    void testGetAllUserViewDto() {
        UserEntity adminUser = getAdmin();

        UserEntity normalUser = getUserEntity();

        List<UserEntity> allUsers = List.of(adminUser, normalUser);

        Mockito.when(userRepository.findAll()).thenReturn(allUsers);

        Mockito.when(userRepository.findByUsername(ConstantTest.ADMIN_USERNAME))
                .thenReturn(Optional.of(adminUser));
        Mockito.when(userRepository.findByUsername(ConstantTest.TEST_USERNAME))
                .thenReturn(Optional.of(normalUser));

        List<UserViewDto> userViewList = userService.getAllUserViewDto();

        Assertions.assertNotNull(userViewList);
        assertEquals(2, userViewList.size());

        assertEquals(ConstantTest.ADMIN_USERNAME, userViewList.get(0).getUsername());
        assertTrue(userViewList.get(0).isAdmin());

        assertEquals(ConstantTest.TEST_USERNAME, userViewList.get(1).getUsername());
        assertFalse(userViewList.get(1).isAdmin());
    }

    @Test
    void testEditUserDetailsDto() {
        UserEntity user = getUserEntityById();

        UserDetailsDto userDetails = new UserDetailsDto()
                .setModified(LocalDateTime.now())
                .setName(user.getName())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail());

        UserDetailsDto result = userService.editUserDetailsDtoById(10L, userDetails);

        Assertions.assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());

        Assertions.assertNotNull(user.getModified());
        assertEquals(ConstantTest.TEST_NAME, user.getName());
        assertEquals(ConstantTest.TEST_USERNAME, user.getUsername());
        assertEquals(ConstantTest.USER_EMAIL, user.getEmail());

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void testEditProfilePhoto() {
        UserEntity user = getUserEntityById();

        user.setImageUrl("Image URL");

        String result = userService.editProfilePhotoById(10L, "Image URL");

        Assertions.assertNotNull(result);
        assertEquals("Image URL", user.getImageUrl());
        assertEquals(Constant.UPDATE_PROFILE_PHOTO, result);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void testDeactivateCurrentUser_True() {
        UserEntity user = getUserEntityById();

        user.setStatus(UserStatus.ACTIVE);
        boolean isDeactivate = userService.deactivateCurrentUserById(user.getId());

        assertTrue(isDeactivate);
        assertEquals(user.getStatus(), UserStatus.DEACTIVATED);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    void testDeactivateCurrentUser_False() {
        UserEntity user = getUserEntityById();

        user.setStatus(UserStatus.PENDING);
        boolean isDeactivate = userService.deactivateCurrentUserById(user.getId());

        assertFalse(isDeactivate);
        assertEquals(user.getStatus(), UserStatus.PENDING);
    }

    @Test
    void testActivateUserByToken_Success() {
        String token = "abc123";
        UserEntity user = getUserEntity()
                .setActivationToken(token)
                .setStatus(UserStatus.PENDING);

        when(userRepository.findByActivationToken(token)).thenReturn(Optional.of(user));

        userService.activateUserByToken(token);

        assertEquals(UserStatus.ACTIVE, user.getStatus());
        Assertions.assertNull(user.getActivationToken());
        verify(userRepository).save(user);
    }

    @Test
    void testActivateUserByToken_InvalidTokenThrows() {
        String token = "invalid";
        when(userRepository.findByActivationToken(token)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.activateUserByToken(token));
        verify(userRepository, never()).save(any());
    }

    private void registerUser(UserRegisterDto testUser) {
        UserRole role = new UserRole(UserRoleEnum.USER);
        testUser.setRoles(List.of(role));

        when(passwordEncoder.encode(testUser.getPassword()))
                .thenReturn(testUser.getPassword() + testUser.getPassword());

        userService.registerUser(testUser);
    }

    private UserRegisterDto getUserRegisterDto(String username, String email) {
        return new UserRegisterDto()
                .setUsername(username)
                .setEmail(email)
                .setName(ConstantTest.TEST_NAME)
                .setPassword(ConstantTest.TEST_PASSWORD)
                .setRoles(List.of(new UserRole(UserRoleEnum.USER)));
    }

    private static UserEntity getAdmin() {
        return new UserEntity()
                .setUsername(ConstantTest.ADMIN_USERNAME)
                .setName(ConstantTest.ADMIN_NAME)
                .setEmail(ConstantTest.ADMIN_EMAIL)
                .setRoles(List.of(
                        new UserRole(UserRoleEnum.USER),
                        new UserRole(UserRoleEnum.ADMINISTRATOR)
                ));
    }

    private static UserEntity getUserEntity() {
        UserEntity user = new UserEntity()
                .setUsername(ConstantTest.TEST_USERNAME)
                .setName(ConstantTest.TEST_NAME)
                .setEmail(ConstantTest.USER_EMAIL)
                .setRoles(List.of(new UserRole(UserRoleEnum.USER)));
        user.setId(10L);
        return user;
    }
    private UserEntity getUserEntityById() {
        UserEntity user = getUserEntity();

        Mockito.when(userRepository.findById(10L))
                .thenReturn(Optional.of(user));
        return user;
    }

    @AfterEach
    void tearDown() {
        reset(userRepository, roleRepository, passwordEncoder, userRoleService);
    }
}
