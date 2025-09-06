package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.config.SecurityUserDetails;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderMovieDto createOrder,
                                         @AuthenticationPrincipal SecurityUserDetails user) {

        this.orderService.createUserOrder(createOrder, user.getUsername());
        return ResponseEntity.ok(Map.of("message", "Order created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderMovieDto> getOrderById(@PathVariable long id) {
        OrderMovieDto orderDto = this.orderService.getOrderDtoById(id);
        return ResponseEntity.ok(orderDto);
    }
}