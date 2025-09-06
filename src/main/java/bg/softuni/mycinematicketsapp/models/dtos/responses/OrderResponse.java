package bg.softuni.mycinematicketsapp.models.dtos.responses;

import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;

public class OrderResponse {
    private String message;
    private OrderMovieDto order;

    public OrderResponse(String message, OrderMovieDto order) {
        this.message = message;
        this.order = order;
    }

    public String getMessage() {
        return message;
    }

    public OrderResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public OrderMovieDto getOrder() {
        return order;
    }

    public OrderResponse setOrder(OrderMovieDto order) {
        this.order = order;
        return this;
    }
}
