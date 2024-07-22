package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.MovieService;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private OrderServiceImpl toTest;
    @Mock
    private UserService mockUserService;
    @Captor
    private ArgumentCaptor<Order> orderEntityCaptor;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;
    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private MovieService mockMovieService;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CityService mockCityService;

    @BeforeEach
    void setUp() {

        this.toTest = new OrderServiceImpl(
                this.mockOrderRepository,
                this.mockMovieService,
                this.mockUserService,
                this.mockCityService
        );
        this.userEntityCaptor = ArgumentCaptor.forClass(UserEntity.class);

    }

    @Test
    public void testCreateUserOrder() {
        // Arrange
        OrderMovieDto orderMovieDto = createTestOrderMovie();
        UserEntity userEntity = createTestUserEntity();

        this.saveOrderToMockDb(userEntity, orderMovieDto);

        Order actualOrder = orderEntityCaptor.getValue();

        Assertions.assertNotNull(actualOrder);
        Assertions.assertEquals(orderMovieDto.getLocation(), actualOrder.getCity().getLocation());
        Assertions.assertEquals(orderMovieDto.getProjectionDate(), actualOrder.getProjectionDate());
    }




    @Test
    void testGetOrderById() {
        OrderMovieDto orderMovieDto = createTestOrderMovie();
        UserEntity userEntity = createTestUserEntity();

        this.saveOrderToMockDb(userEntity, orderMovieDto);

        Order actualOrder = orderEntityCaptor.getValue();

        Mockito.when(this.mockOrderRepository.findById(actualOrder.getId())).thenReturn(Optional.of(actualOrder));

        Order orderById = this.toTest.getOrderById(actualOrder.getId());

        Assertions.assertNotNull(orderById);
        Assertions.assertEquals(orderById.getCity(), actualOrder.getCity());
        Assertions.assertEquals(orderById.getId(), actualOrder.getId());
    }
    @Test
    void testGetUnfinishedOrderByUser() {
        OrderMovieDto orderMovieDto = createTestOrderMovie();
        UserEntity userEntity = createTestUserEntity();

        this.saveOrderToMockDb(userEntity, orderMovieDto);

        Order actualOrder = orderEntityCaptor.getValue();
        actualOrder.setUser(userEntity);

        Mockito.when(this.mockOrderRepository.findById(actualOrder.getId())).thenReturn(Optional.of(actualOrder));

        Order orderById = this.toTest.getOrderById(actualOrder.getId());
        // Act
        OrderMovieDto unfinishedOrderByUser = this.toTest.getUnfinishedOrderByUser(userEntity.getUsername());
        // Assert

        Assertions.assertNotNull(unfinishedOrderByUser);

    }
    @Test
    void testGetOrderMovieById() {

    }
    @Test
    void testDeleteOrderById() {

    }

    private void saveOrderToMockDb(UserEntity userEntity, OrderMovieDto orderMovieDto) {
        when(this.mockUserService.getUserByUsername(userEntity.getUsername()))
                .thenReturn(new UserEntity());
        when(this.mockCityService.getCityByCityName(orderMovieDto.getLocation()))
                .thenReturn(new City(orderMovieDto.getLocation()));

        // Act
        this.toTest.createUserOrder(createTestOrderMovie(), userEntity.getUsername());

        // Assert
        verify(this.mockOrderRepository).save(orderEntityCaptor.capture());
    }

    private UserEntity createTestUserEntity() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("pesho");
        return userEntity;
    }

    private Order createTestOrder(OrderMovieDto orderMovieDto) {
        City city = new City(orderMovieDto.getLocation());
        return new Order()
                .setOrderNumber(orderMovieDto.getOrderNumber())
                .setCity(city);
    }
    private OrderMovieDto createTestOrderMovie() {
        OrderMovieDto orderMovieDto = new OrderMovieDto();
        orderMovieDto.setLocation(CityName.SOFIA);
        orderMovieDto.setProjectionDate(LocalDate.of(2024, 8,12));
        return orderMovieDto;
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(this.mockOrderRepository);

    }
}