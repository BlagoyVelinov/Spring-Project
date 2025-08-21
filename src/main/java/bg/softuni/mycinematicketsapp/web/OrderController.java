package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.config.SecurityUserDetails;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;
    private final TicketService ticketService;

    @Autowired
    public OrderController(OrderService orderService, TicketService ticketService) {
        this.orderService = orderService;
        this.ticketService = ticketService;
    }



    @GetMapping("/confirm-order/{orderId}")
    public ResponseEntity<?> orderTickets(@PathVariable("orderId") long orderId) {
        OrderMovieDto orderViewDto = this.orderService.getOrderMovieById(orderId);
        if (orderViewDto == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, List<TicketViewDto>> ticketsMap = this.ticketService.addToTicketsMap(orderId);
        Map<Integer, List<Integer>> rowAndSeats = this.ticketService.getSeatsByRow(orderId);

        return ResponseEntity.ok(Map.of(
                "orderDto", orderViewDto,
                "ticketsMap", ticketsMap,
                "rowAndSeats", rowAndSeats
        ));
    }

    @GetMapping("/show-tickets/{orderId}")
    public ResponseEntity<?> showTickets(@PathVariable("orderId") long orderId) {
        OrderMovieDto orderViewDto = this.orderService.getOrderMovieById(orderId);
        if (orderViewDto == null) {
            return ResponseEntity.notFound().build();
        }

        this.ticketService.confirmOrder(orderId);
        return ResponseEntity.ok("Tickets confirmed and order completed.");
    }


    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderMovieDto createOrder,
                                         @AuthenticationPrincipal SecurityUserDetails user) {

        this.orderService.createUserOrder(createOrder, user.getUsername());
        return ResponseEntity.ok(Map.of("message", "Order created successfully"));
    }
}