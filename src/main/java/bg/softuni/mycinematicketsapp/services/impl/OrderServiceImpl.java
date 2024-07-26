package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.*;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.UserService;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MovieService movieService;
    private final UserService userService;
    private final CityService cityService;

    public OrderServiceImpl(OrderRepository orderRepository, MovieService movieService,
                            UserService userService, CityService cityService) {
        this.orderRepository = orderRepository;
        this.movieService = movieService;
        this.userService = userService;
        this.cityService = cityService;
    }

    @Override
    public Order getOrderById(long orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException("Order with Id: " + orderId + " not exist!"));
    }

    @Override
    public void createUserOrder(OrderMovieDto createOrder, String username) {
        UserEntity user = this.userService.getUserByUsername(username);
        Order order = this.mapOrderMovieDtoToOrder(createOrder, user);

        this.orderRepository.save(order);
    }

    @Override
    public OrderMovieDto getUnfinishedOrderByUser(String username) {
        UserEntity user = this.userService.getUserByUsername(username);

        Order order = user.getOrders()
                .stream()
                .filter(ord -> !ord.isFinished())
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException("Order is not found!"));

        return this.mapOrderToOrderDto(order);
    }

    @Override
    public OrderMovieDto getOrderMovieById(long orderId, long movieId, long timeId) {
        Order order = this.getOrderById(orderId);
        MovieViewDto movieView = this.movieService.getMovieViewById(movieId);
        BookingTimeDto bookingTime = this.movieService.getBookingTimeById(timeId);
        order.setMovieName(movieView.getName());
        order.setBookingTime(bookingTime.getBookingTimeValue());
        this.orderRepository.save(order);
        return this.mapOrderToOrderDto(order);
    }

    @Override
    public OrderMovieDto getOrderMovieById(long orderId) {
        Order order = this.getOrderById(orderId);
        return this.mapOrderToOrderDto(order);
    }


    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void deleteAllNotFinishedOrders() {
        List<Order> notFinishedOrders = this.orderRepository.findAll();
        notFinishedOrders.stream()
                .filter(order -> !order.isFinished())
                .forEach(order -> {
                    this.orderRepository.deleteById(order.getId());
                });
    }

    @Override
    public void addQuantityOfTickets(OrderMovieDto orderMovie, long orderId, long movieId, long timeId) {
        Order order = this.getOrderById(orderId);
        double totalPricePlusTax = this.getTotalPricePlusTax(order);

        order.setChildQuantity(orderMovie.getChildQuantity());
        order.setOverSixtyQuantity(orderMovie.getOverSixtyQuantity());
        order.setRegularQuantity(orderMovie.getRegularQuantity());
        order.setStudentQuantity(orderMovie.getStudentQuantity());
        order.setTotalPrice(totalPricePlusTax);

        this.orderRepository.save(order);
    }

    @Override
    public OrderMovieDto getOrderByOrderNumber(String orderNumber) {
        Order order = this.orderRepository.findByOrderNumber(orderNumber);
        return this.mapOrderToOrderDto(order);
    }

    private double getTotalPricePlusTax(Order order) {
        int countTickets = order.getTickets().size();

        double totalPrice = order.getTickets()
                .stream()
                .mapToDouble(Ticket::getPrice)
                .sum();

        return totalPrice + (countTickets * Constant.FIX_COMMISSION_PER_TICKET);
    }

    private OrderMovieDto mapOrderToOrderDto(Order order) {
        UserViewDto userViewDto = this.mapUserToUserViewDto(order.getUser());

        OrderMovieDto orderMovieDto = new OrderMovieDto()
                .setId(order.getId())
                .setBookingTime(order.getBookingTime())
                .setOrderNumber(order.getOrderNumber())
                .setLocation(order.getCity().getLocation())
                .setProjectionDate(order.getProjectionDate())
                .setMovieViewName(order.getMovieName())
                .setUser(userViewDto);
        orderMovieDto.setChildQuantity(order.getChildQuantity());
        orderMovieDto.setRegularQuantity(order.getRegularQuantity());
        orderMovieDto.setOverSixtyQuantity(order.getOverSixtyQuantity());
        orderMovieDto.setStudentQuantity(order.getStudentQuantity());
        orderMovieDto.setTotalPrice(order.getTotalPrice());

        return orderMovieDto;


//        return this.modelMapper.map(order, OrderMovieDto.class);
    }

    private UserViewDto mapUserToUserViewDto(UserEntity user) {
        return new UserViewDto()
                .setName(user.getName())
                .setEmail(user.getEmail());
    }

    private Order mapOrderMovieDtoToOrder(OrderMovieDto createOrder, UserEntity user) {
        City city = this.cityService.getCityByCityName(createOrder.getLocation());
        Order order = user.getOrders()
                .stream()
                .filter(ord -> !ord.isFinished())
                .findFirst()
                .orElse(null);


        if (order != null) {
            order.setCity(city);
            order.setProjectionDate(createOrder.getProjectionDate());
            return order;
        }

        String orderNumberUUID = UUID.randomUUID().toString();
        orderNumberUUID = orderNumberUUID.replace("-", "");

        return new Order()
                .setOrderNumber(orderNumberUUID)
                .setCity(city)
                .setProjectionDate(createOrder.getProjectionDate())
                .setUser(user);
    }
}
