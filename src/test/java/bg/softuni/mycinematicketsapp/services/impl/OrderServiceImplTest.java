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
import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderServiceImpl orderServiceTest;
    @Mock
    private UserService mockUserService;
    @Captor
    private ArgumentCaptor<Order> orderEntityCaptor;
    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private CityService mockCityService;
    @Mock
    private UserRepository mockUserRepository;


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
        UserRegisterDto testUser = this.createTestUser();
        this.mockUserService.registerUser(testUser);


        OrderMovieDto testOrder = this.createTestOrderDto();

        this.orderServiceTest.createUserOrder(testOrder, testUser.getUsername());

        verify(this.mockOrderRepository).save(this.orderEntityCaptor.capture());


        Order actualOrder = this.orderEntityCaptor.getValue();

        Assertions.assertNotNull(actualOrder);
        Assertions.assertEquals(testOrder.getOrderNumber(), actualOrder.getOrderNumber());
        Assertions.assertEquals(testOrder.getLocation(), actualOrder.getCity().getLocation());
        Assertions.assertEquals(testOrder.getProjectionDate(), actualOrder.getProjectionDate());
    }

    @Test
    void testGetOrderById() {
        OrderMovieDto testOrder = this.createTestOrderDto();


        Order actualOrder = this.orderEntityCaptor.getValue();

        Assertions.assertEquals(testOrder.getId(), actualOrder.getId());
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


    private UserRegisterDto createTestUser() {
        return new UserRegisterDto()
                .setUsername("tester")
                .setEmail("firstLast@example.com")
                .setName("fullName")
                .setPassword("123456789")
                .setConfirmPassword("123456789")
                .setRoles(List.of(
                        new UserRole().setRole(UserRoleEnum.ADMINISTRATOR),
                        new UserRole().setRole(UserRoleEnum.USER)
                ));
    }
}