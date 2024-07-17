package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.UserService;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CityService cityService;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, UserService userService,
                            CityService cityService, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cityService = cityService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Order getOrderById(long orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException("Order with " + orderId + "not exist!"));
    }

    @Override
    public void createUserOrder(OrderMovieDto createOrder, String username) {
        UserEntity user = this.userService.getUserByUsername(username);
        Order order = this.mapOrderMovieDtoToOrder(createOrder, user);

        if (order.getId() == 0) {
            this.orderRepository.save(order);
        }
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

        return null;
    }


    @Override
    @Transactional
    public void deleteOrderById(long orderId) {
        Order order = this.getOrderById(orderId);
        order.setUser(null)
                .setMovie(null)
                .setCity(null)
                .setTickets(null);
        this.orderRepository.delete(order);
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
