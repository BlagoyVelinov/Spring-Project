package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;
import bg.softuni.mycinematicketsapp.services.BookingTimeService;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final MovieService movieService;
    private final OrderService orderService;
    private final BookingTimeService bookingTimeService;

    @Autowired
    public OrderController(MovieService movieService, OrderService orderService, BookingTimeService bookingTimeService) {
        this.movieService = movieService;
        this.orderService = orderService;
        this.bookingTimeService = bookingTimeService;
    }
    @GetMapping("/buy-tickets/{orderId}/movie/{movieId}/time/{timeId}")
    public String buyTickets(@PathVariable long orderId, @PathVariable long movieId, @PathVariable long timeId, Model model) {

        OrderMovieDto orderMovie = this.orderService.getOrderMovieById(orderId, movieId, timeId);

        BookingTimeEnum bookingTime = this.bookingTimeService.getBookingTimeEnumById(timeId);
        model.addAttribute("bookingTime", bookingTime);

        MovieViewDto movieView = this.movieService.getMovieViewById(movieId);
        model.addAttribute("movieView", movieView);
        return "buy-tickets";
    }

    @GetMapping("/select-seats")
    public String getSelectSeat() {
        return "select-seat";
    }
}
