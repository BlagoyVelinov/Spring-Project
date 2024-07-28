package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.*;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final MovieService movieService;
    private final OrderService orderService;
    private final TicketService ticketService;
//    private UpdateTicketDto updateTicketDto;

    @Autowired
    public OrderController(MovieService movieService, OrderService orderService, TicketService ticketService) {
        this.movieService = movieService;
        this.orderService = orderService;
        this.ticketService = ticketService;
//        this.updateTicketDto = new UpdateTicketDto(3, 4);
    }

//    @ModelAttribute("updateTicket")
//    public UpdateTicketDto initTicket() {
//        return new UpdateTicketDto(3,4);
//    }

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

        model.addAttribute("updateTicket", new UpdateTicketDto(3, 4));

        int ticketsQuantity = this.orderService.getCountOfTicketsByOrderId(orderId);
        model.addAttribute("ticketsCount", ticketsQuantity);

        OrderMovieDto orderMovieDto = this.orderService.getOrderMovieById(orderId);
        model.addAttribute("orderDto", orderMovieDto);

        MovieViewDto movieView = this.movieService.getMovieViewById(movieId);
        model.addAttribute("movieView", movieView);

        return "select-seat";
    }


//      @PostMapping("/matrix")
//  public String submitMatrix(MatrixFormDTO matrixForm, Model model) {

//    boolean[][] submittedMatrix = matrixForm.getMatrix();

//    // Add logic here to process the submitted matrix as needed

//    model.addAttribute("matrixForm", matrixForm);
//    return "matrix";
//  }



    @PostMapping("/select-seats/{orderId}/movie/{movieId}")
    public String addSeatToTicket(@PathVariable long orderId,
                                  @PathVariable("movieId") long movieId,
                                  UpdateTicketDto updateTicket, Model model) {
        boolean[][] matrix = updateTicket.getSeats();

        // @ModelAttribute("...") With this code return initial matrix but only false
        updateTicket = (UpdateTicketDto) model.getAttribute("updateTicket");


        int ticketsQuantity = this.orderService.getCountOfTicketsByOrderId(orderId);

        if (updateTicket.getCountOfTickets(matrix) != ticketsQuantity) {
            return Constant.REDIRECT_SELECT_SEATS;
        }
        this.ticketService.updateTickets(updateTicket, orderId, movieId);
        model.addAttribute("updateTicket", updateTicket);

//        This is test with global variable but result is the same!
//        this.ticketService.updateTicketsWithSeats(matrix, orderId, movieId);
//        model.addAttribute("updateTicket", this.updateTicketDto);

        return Constant.REDIRECT_CONFIRM_ORDER;
    }

    @GetMapping("/confirm-order/{orderId}")
    public String orderTickets(@PathVariable("orderId") long orderId, Model model) {
        OrderMovieDto orderViewDto = this.orderService.getOrderMovieById(orderId);
        model.addAttribute("orderView", orderViewDto);
        return "confirm-order";
    }

    private boolean checkCountOfTicketsIsGraterThanZero(OrderMovieDto orderMovie) {
        return orderMovie.getRegularQuantity()
                + orderMovie.getChildQuantity()
                + orderMovie.getStudentQuantity()
                + orderMovie.getOverSixtyQuantity() == 0;
    }
}
