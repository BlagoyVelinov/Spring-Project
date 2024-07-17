package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.UserRegisterDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.repository.UserRoleRepository;
import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.UserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private static final String USER_WITH_USERNAME = "useruser";
    private static final String USER_EMAIL = "useruser@gmail.com";

    private OrderServiceImpl orderServiceTest;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;
    @Mock
    private UserService mockUserService;
//    @Captor
//    private ArgumentCaptor<Order> orderEntityCaptor;
    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private CityService mockCityService;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserRoleRepository mockRoleRepository;


    @BeforeEach
    void setUp() {

        this.orderServiceTest = new OrderServiceImpl(
                this.mockOrderRepository,
                this.mockUserService,
                this.mockCityService,
                new ModelMapper()
        );

    }

    @Test
    void testCreateUserOrderCorrectly() {
        UserRegisterDto testUser = this.getUserRegisterDto(USER_WITH_USERNAME, USER_EMAIL);
        this.registerUser(testUser, UserRoleEnum.USER);

        verify(this.mockUserRepository).save(this.userEntityCaptor.capture());

        UserEntity actualSaveEntity = this.userEntityCaptor.getValue();

        Mockito.when(this.mockUserRepository.findByUsername(USER_WITH_USERNAME)).thenReturn(Optional.of(actualSaveEntity));

        actualSaveEntity.setRoles(testUser.getRoles());

        OrderMovieDto testOrder = this.createTestOrderDto();

        this.orderServiceTest.createUserOrder(testOrder, actualSaveEntity.getUsername());

//        verify(this.mockOrderRepository).save(this.orderEntityCaptor.capture());
//
//
//        Order actualOrder = this.orderEntityCaptor.getValue();

//        Assertions.assertNotNull(actualOrder);
//        Assertions.assertEquals(testOrder.getOrderNumber(), actualOrder.getOrderNumber());
//        Assertions.assertEquals(testOrder.getLocation(), actualOrder.getCity().getLocation());
//        Assertions.assertEquals(testOrder.getProjectionDate(), actualOrder.getProjectionDate());
    }

    @Test
    void testGetOrderById() {
        OrderMovieDto testOrder = this.createTestOrderDto();

    }
    @Test
    void testGetUnfinishedOrderByUser() {

    }
    @Test
    void testGetOrderMovieById() {

    }
    @Test
    void testDeleteOrderById() {

    }

    private OrderMovieDto createTestOrderDto() {
        String lUUID = String.format("%020d", new BigInteger(
                UUID.randomUUID().toString().replace("-", ""), 16));
        lUUID = lUUID.substring(20);

        LocalDate projectionDate = LocalDate.of(2024, 7, 20);


        return new OrderMovieDto()
                .setId(1)
                .setOrderNumber(lUUID)
                .setLocation(CityName.SOFIA)
                .setProjectionDate(projectionDate);

    }


    private void registerUser(UserRegisterDto testUser, UserRoleEnum roleEnum) {
        UserRole userRole = new UserRole(roleEnum);
        testUser.setRoles(List.of(userRole));

        when(this.mockPasswordEncoder.encode(testUser.getPassword()))
                .thenReturn(testUser.getPassword() + testUser.getPassword());

        this.mockUserService.registerUser(testUser);
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