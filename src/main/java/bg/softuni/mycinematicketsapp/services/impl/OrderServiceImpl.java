package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.services.CityService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import bg.softuni.mycinematicketsapp.services.UserService;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static bg.softuni.mycinematicketsapp.constants.ExceptionMessages.ORDER_NOT_EXIST;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final CityService cityService;
    private final TicketService ticketService;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserService userService, CityService cityService, TicketService ticketService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.cityService = cityService;
        this.ticketService = ticketService;
    }

    @Override
    public Order getOrderById(long orderId) {
        return this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(ORDER_NOT_EXIST, orderId)));
    }

    @Override
    public void createUserOrder(OrderMovieDto createOrder, String username) {
        UserEntity user = this.userService.getUserByUsername(username);
        Order order = this.mapOrderMovieDtoToOrder(createOrder, user);

        this.ticketService.saveTicketsForOrder(order);

        this.orderRepository.save(order);

    }

    @Override
    public OrderMovieDto getOrderDtoById(long id) {
        Order order = this.getOrderById(id);
        return this.mapOrderToOrderDto(order);
    }


    /**
     * This method start every day at 00:00h by dint of Scheduling and cron!
     * Check for not finished orders in all DB and delete them!
     */

//    @Scheduled(cron = "0 0 0 * * *")
//    public void deleteAllNotFinishedOrders() {
//        List<Order> notFinishedOrders = this.orderRepository.findAll();
//        notFinishedOrders.stream()
//                .filter(order -> !order.isFinished())
//                .forEach(order -> {
//                    this.orderRepository.deleteById(order.getId());
//                });
//    }

    private OrderMovieDto mapOrderToOrderDto(Order order) {
        UserViewDto userViewDto = this.mapUserToUserViewDto(order.getUser());


        OrderMovieDto orderMovieDto = new OrderMovieDto()
                .setId(order.getId())
                .setBookingTime(order.getBookingTime())
                .setOrderNumber(order.getOrderNumber())
                .setLocation(order.getCity().getLocation())
                .setProjectionDate(order.getProjectionDate())
                .setMovieViewName(order.getMovieName())
                .setUser(userViewDto)
                .setTickets(order.getTickets())
                .setMovieId(order.getMovieId())
                .setBookingTimeId(order.getBookingTimeId());
        orderMovieDto.setChildQuantity(order.getChildQuantity());
        orderMovieDto.setRegularQuantity(order.getRegularQuantity());
        orderMovieDto.setOverSixtyQuantity(order.getOverSixtyQuantity());
        orderMovieDto.setStudentQuantity(order.getStudentQuantity());
        orderMovieDto.setTotalPrice(order.getTotalPrice());

        return orderMovieDto;

    }

    private UserViewDto mapUserToUserViewDto(UserEntity user) {
        return new UserViewDto()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setName(user.getName())
                .setEmail(user.getEmail());
    }

    private Order mapOrderMovieDtoToOrder(OrderMovieDto createOrder, UserEntity user) {
        City city = this.cityService.getCityByCityName(createOrder.getLocation());

        String orderNumberUUID = UUID.randomUUID().toString();
        orderNumberUUID = orderNumberUUID.replace(Constant.UUID_REPLACE, Constant.UUID_REPLACEMENT);

        Order newOrder = new Order();
        newOrder.setOrderNumber(orderNumberUUID)
                .setCity(city)
                .setProjectionDate(createOrder.getProjectionDate())
                .setUser(user)
                .setMovieId(createOrder.getMovieId())
                .setBookingTimeId(createOrder.getBookingTimeId())
                .setMovieName(createOrder.getMovieViewName())
                .setChildQuantity(createOrder.getChildQuantity());
        newOrder.setOverSixtyQuantity(createOrder.getOverSixtyQuantity());
        newOrder.setRegularQuantity(createOrder.getRegularQuantity());
        newOrder.setStudentQuantity(createOrder.getStudentQuantity());
        newOrder.setTotalPrice(createOrder.getTotalPrice());
        newOrder.setBookingTime(createOrder.getBookingTime());
        newOrder.setTickets(createOrder.getTickets());

        newOrder.setFinished(isCompleteOrder(newOrder));

        return newOrder;
    }

    private static boolean isCompleteOrder(Order order) {
        return order.getMovieId() != 0 &&
                order.getBookingTimeId() != 0 &&
                order.getMovieName() != null &&
                order.getProjectionDate() != null &&
                order.getCity() != null &&
                order.getTotalPrice() > 0 &&
                !order.getTickets().isEmpty() &&
                (order.getChildQuantity() > 0 ||
                        order.getOverSixtyQuantity() > 0 ||
                        order.getRegularQuantity() > 0 ||
                        order.getStudentQuantity() > 0);
    }
}
