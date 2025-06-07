package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;

public interface OrderService {

    Order getOrderById(long orderId);

    void createUserOrder(OrderMovieDto createOrder, String username);

    OrderMovieDto getUnfinishedOrderByUser(String username);

    OrderMovieDto getOrderMovieById(long orderId);

    void deleteAllNotFinishedOrders();

    int getCountOfTicketsByOrderId(long orderId);

}
