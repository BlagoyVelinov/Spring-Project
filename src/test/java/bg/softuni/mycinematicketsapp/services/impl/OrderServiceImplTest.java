package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.models.enums.TicketType;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import bg.softuni.mycinematicketsapp.services.UserService;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    private OrderRepository orderRepository;
    private UserService userService;
    private CityService cityService;
    private TicketService ticketService;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        userService = mock(UserService.class);
        cityService = mock(CityService.class);
        ticketService = mock(TicketService.class);

        orderService = new OrderServiceImpl(orderRepository, userService, cityService, ticketService);
    }

    @Test
    void testGetOrderById_Found() {
        Order order = new Order();
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> orderService.getOrderById(99L));
        verify(orderRepository, times(1)).findById(99L);
    }

    @Test
    void testCreateUserOrder_Success() {
        String username = "John";
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername(username);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        City city = new City();
        city.setId(10L);
        city.setLocation(CityName.SOFIA);

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setMovieName("Inception");
        ticket.setHallNumber("1");
        ticket.setNumberOfSeat(12);
        ticket.setNumberOfRow(5);
        ticket.setPrice(10.0);
        ticket.setProjectionDate(LocalDate.now());
        ticket.setMovieClassDescription("IMAX");
        ticket.setBookingTime(LocalTime.of(19, 30));
        ticket.setTicketType(TicketType.REGULAR);
        ticket.setLocation(CityName.SOFIA);
        ticket.setFinished(false);
        ticket.setUserId(1L);

        OrderMovieDto orderDto = new OrderMovieDto()
                .setLocation(CityName.SOFIA)
                .setMovieId(5L)
                .setMovieViewName("Inception")
                .setProjectionDate(LocalDate.now())
                .setBookingTime("12:20")
                .setBookingTimeId(100L)
                .setTickets(List.of(ticket));
        orderDto.setTotalPrice(5.7);
        orderDto.setChildQuantity(1);
        orderDto.setRegularQuantity(0);
        orderDto.setStudentQuantity(0);
        orderDto.setOverSixtyQuantity(0);

        when(userService.getUserByUsername(username)).thenReturn(user);
        when(cityService.getCityByCityName(CityName.SOFIA)).thenReturn(city);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order currOrder = invocation.getArgument(0);
            currOrder.setId(123L);
            return currOrder;
        });

        OrderMovieDto result = orderService.createUserOrder(orderDto, username);

        assertNotNull(result);
        assertEquals(123L, result.getId());
        assertEquals("Sofia", result.getLocation().getValue());
        assertEquals("Inception", result.getMovieViewName());
        assertEquals(user.getUsername(), result.getUser().getUsername());
        assertEquals(5.7, result.getTotalPrice());

        verify(userService).getUserByUsername(username);
        verify(cityService).getCityByCityName(CityName.SOFIA);
        verify(ticketService).saveTicketsForOrder(any(Order.class));
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void testGetOrderDtoById_Success() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("john");
        user.setName("John");
        user.setEmail("john@example.com");

        City city = new City();
        city.setLocation(CityName.SOFIA);

        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setMovieName("Inception");
        ticket.setHallNumber("1");
        ticket.setNumberOfSeat(12);
        ticket.setNumberOfRow(5);
        ticket.setPrice(10.0);
        ticket.setProjectionDate(LocalDate.now());
        ticket.setMovieClassDescription("IMAX");
        ticket.setBookingTime(LocalTime.of(19, 30));
        ticket.setTicketType(TicketType.REGULAR);
        ticket.setLocation(CityName.SOFIA);
        ticket.setFinished(false);
        ticket.setUserId(1L);

        Order order = new Order();
        order.setId(42L);
        order.setUser(user);
        order.setCity(city);
        order.setMovieId(7L);
        order.setMovieName("Avatar");
        order.setProjectionDate(LocalDate.now());
        order.setBookingTime("12:20");
        order.setBookingTimeId(200L);
        order.setChildQuantity(2);
        order.setRegularQuantity(0);
        order.setStudentQuantity(0);
        order.setOverSixtyQuantity(0);
        order.setTickets(List.of(ticket));
        order.setTotalPrice(20.0);

        when(orderRepository.findById(42L)).thenReturn(Optional.of(order));

        OrderMovieDto result = orderService.getOrderDtoById(42L);

        assertNotNull(result);
        assertEquals(42L, result.getId());
        assertEquals("Avatar", result.getMovieViewName());
        assertEquals("Sofia", result.getLocation().getValue());
        assertEquals(20, result.getTotalPrice());
        assertEquals("john", result.getUser().getUsername());

        verify(orderRepository).findById(42L);
    }
}