package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.*;
import bg.softuni.mycinematicketsapp.models.dtos.view.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final MovieService movieService;
    private final OrderService orderService;
    private final TicketService ticketService;

    @Autowired
    public OrderController(MovieService movieService, OrderService orderService, TicketService ticketService) {
        this.movieService = movieService;
        this.orderService = orderService;
        this.ticketService = ticketService;
    }

    @ModelAttribute("updateTicket")
    public UpdateTicketDto initTicket() {
        return new UpdateTicketDto(10,20);
    }

    @ModelAttribute("orderMovie")
    public OrderMovieDto updateOrderMovieDto() {
        return new OrderMovieDto();
    }


    @GetMapping("/buy-tickets/{orderId}/movie/{movieId}/time/{timeId}")
    public String buyTickets(@PathVariable long orderId,
                             @PathVariable long movieId,
                             @PathVariable long timeId, Model model) {

        OrderMovieDto orderMovie = this.orderService.getOrderMovieById(orderId, movieId, timeId);
        model.addAttribute("orderMovie", orderMovie);


        BookingTimeDto bookingTime = this.movieService.getBookingTimeById(timeId);
        model.addAttribute("bookingTime", bookingTime);

        MovieViewDto movieView = this.movieService.getMovieViewById(movieId);
        model.addAttribute("movieView", movieView);
        return "buy-tickets";
    }

    @PutMapping("/buy-tickets/{orderId}/movie/{movieId}/time/{timeId}")
    public String createTickets(@PathVariable long orderId,
                                @PathVariable long movieId,
                                @PathVariable long timeId,
                                OrderMovieDto orderMovie,
                                BindingResult bindingResult,
                                TicketViewDto createTicket) {

        if (this.checkCountOfTicketsIsGraterThanZero(orderMovie)) {
            bindingResult.addError(new FieldError(
                    Constant.OBJECT_ZERO_QUANTITY,
                    Constant.FIELD_QUANTITY,
                    Constant.DEFAULT_MESSAGE_QUANTITY));
        }
        if (bindingResult.hasErrors()) {
            return Constant.REDIRECT_BUY_TICKETS;
        }
        this.orderService.addQuantityOfTickets(orderMovie, orderId, movieId, timeId);
        this.ticketService.createTickets(createTicket, orderId, movieId);

        return Constant.REDIRECT_SELECT_SEATS;
    }

    @GetMapping("/select-seats/{orderId}/movie/{movieId}")
    public String getSelectSeat(@PathVariable("orderId") long orderId,
                                @PathVariable("movieId") long movieId,
                                Model model) {

        int ticketsQuantity = this.orderService.getCountOfTicketsByOrderId(orderId);
        model.addAttribute("ticketsCount", ticketsQuantity);

        OrderMovieDto orderMovieDto = this.orderService.getOrderMovieById(orderId);
        model.addAttribute("orderDto", orderMovieDto);

        MovieViewDto movieView = this.movieService.getMovieViewById(movieId);
        model.addAttribute("movieView", movieView);

        return "select-seat";
    }



    @PostMapping("/select-seats/{orderId}/movie/{movieId}")
    public String addSeatToTicket(@PathVariable long orderId,
                                  @PathVariable("movieId") long movieId,
                                  UpdateTicketDto updateTicket) {
        String[][] matrix = updateTicket.getSeats();

        int ticketsQuantity = this.orderService.getCountOfTicketsByOrderId(orderId);
        if (updateTicket.getCountOfTickets(matrix) != ticketsQuantity) {
            return Constant.REDIRECT_SELECT_SEATS;
        }
        this.ticketService.updateTickets(updateTicket, orderId, movieId);

        return Constant.REDIRECT_CONFIRM_ORDER;
    }

    @GetMapping("/confirm-order/{orderId}")
    public String orderTickets(@PathVariable("orderId") long orderId, Model model) {
        OrderMovieDto orderViewDto = this.orderService.getOrderMovieById(orderId);
        model.addAttribute("orderDto", orderViewDto);

        Map<String, List<TicketViewDto>> ticketsMap = this.ticketService.addToTicketsMap(orderId);
        model.addAttribute("ticketsMap", ticketsMap);

        Map<Integer, List<Integer>> rowAndSeats = this.ticketService.getSeatsByRow(orderId);
        model.addAttribute("rowAndSeats", rowAndSeats);

        return "confirm-order";
    }
    @GetMapping("/show-tickets/{orderId}")
    public String showTickets(@PathVariable("orderId") long orderId, Model model) {
        OrderMovieDto orderViewDto = this.orderService.getOrderMovieById(orderId);
        model.addAttribute("orderDto", orderViewDto);

        this.ticketService.confirmOrder(orderId);
        return "show-tickets";
    }

    private boolean checkCountOfTicketsIsGraterThanZero(OrderMovieDto orderMovie) {
        return orderMovie.getRegularQuantity()
                + orderMovie.getChildQuantity()
                + orderMovie.getStudentQuantity()
                + orderMovie.getOverSixtyQuantity() == 0;
    }
}
