package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.config.SecurityUserDetails;
import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.responses.OrderResponse;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.SecurityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final SecurityService securityService;

    @Autowired
    public OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderMovieDto createOrder,
                                                     @AuthenticationPrincipal SecurityUserDetails user) {

        OrderMovieDto orderDto = this.orderService.createUserOrder(createOrder, user.getUsername());

        return ResponseEntity.ok(new OrderResponse(Constant.SUCCESS_CREATED_ORDER, orderDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderMovieDto> getOrderById(@PathVariable long id, Authentication authentication) {
        OrderMovieDto orderDto = this.orderService.getOrderDtoById(id);
        long userId = orderDto.getUser().getId();

        this.securityService.validateCurrentUser(userId, authentication);

        return ResponseEntity.ok(orderDto);
    }
}