package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    @ModelAttribute("createTicket")
    public TicketViewDto initTicket() {
        return new TicketViewDto();
    }

    @ModelAttribute("orderMovie")
    public OrderMovieDto updateOrderMovieDto() {
        return new OrderMovieDto();
    }


    @GetMapping("/buy-tickets/{orderId}/movie/{movieId}/time/{timeId}")
    public String buyTickets(@PathVariable long orderId, @PathVariable long movieId, @PathVariable long timeId, Model model) {

        OrderMovieDto orderMovie = this.orderService.getOrderMovieById(orderId, movieId, timeId);
        model.addAttribute("orderMovie", orderMovie);


        BookingTimeDto bookingTime = this.movieService.getBookingTimeById(timeId);
        model.addAttribute("bookingTime", bookingTime);

        MovieViewDto movieView = this.movieService.getMovieViewById(movieId);
        model.addAttribute("movieView", movieView);
        return "buy-tickets";
    }

    @PatchMapping("/buy-tickets/{orderId}/movie/{movieId}/time/{timeId}")
    public String addTicketsQuantity(@PathVariable long orderId,
                                   @PathVariable long movieId,
                                   @PathVariable long timeId,
                                   OrderMovieDto orderMovie) {
        if (orderMovie.getTicketsQuantity() == 0) {
            return Constant.REDIRECT_BUY_TICKETS;
        }
        this.orderService.addQuantityOfTickets(orderMovie.getTicketsQuantity(), orderId, movieId, timeId);
        return Constant.REDIRECT_SELECT_SEATS;
    }

    @GetMapping("/select-seats/{orderId}/movie/{movieId}")
    public String getSelectSeat(@PathVariable("orderId") long orderId,
                                @PathVariable("movieId") long movieId,
                                Model model) {
        OrderMovieDto orderMovieDto = this.orderService.getOrderMovieById(orderId);
        model.addAttribute("orderDto", orderMovieDto);
        return "select-seat";
    }

    @PostMapping("/select-seats/{orderId}/movie/{movieId}")
    public String buyTicket(@PathVariable long orderId,
                            @PathVariable("movieId") long movieId,
                            @Valid TicketViewDto createTicket,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute(Constant.TICKET_ATTRIBUTE_NAME, createTicket)
                    .addFlashAttribute(Constant.TICKET_BINDING_RESULT_NAME, bindingResult);
            return Constant.REDIRECT_BUY_TICKETS;
        }
        this.ticketService.createTickets(createTicket, orderId, movieId);
        return Constant.REDIRECT_SELECT_SEATS;
    }
}
