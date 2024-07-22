package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.Movie;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.UserService;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
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
        order.setMovieName(movieView.getName());
        this.orderRepository.save(order);
        //TODO: change order in DB need to accept only movieName(anything else form movie will go to ticket)
        return this.mapOrderToOrderDto(order);
    }


    @Override
    //TODO: Use Scheduler to delete order every day at 00:00h
    public void deleteAllNotFinishedOrders() {
        List<Order> notFinishedOrders = this.orderRepository.findAll();
        notFinishedOrders.stream()
                .filter(order -> !order.isFinished())
                .forEach(order -> {
                    this.orderRepository.deleteById(order.getId());
                });
    }

    private OrderMovieDto mapOrderToOrderDto(Order order) {
        return new OrderMovieDto()
                .setId(order.getId())
                .setOrderNumber(order.getOrderNumber())
                .setLocation(order.getCity().getLocation())
                .setProjectionDate(order.getProjectionDate());
//        return this.modelMapper.map(order, OrderMovieDto.class);
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

        String lUUID = String.format("%020d", new BigInteger(
                UUID.randomUUID().toString().replace("-", ""), 16));

        lUUID = lUUID.substring(20);

        return new Order()
                .setOrderNumber(lUUID)
                .setCity(city)
                .setProjectionDate(createOrder.getProjectionDate())
                .setUser(user);
    }
}
