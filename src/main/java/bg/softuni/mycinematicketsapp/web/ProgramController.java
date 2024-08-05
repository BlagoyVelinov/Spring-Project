package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequestMapping("/program")
public class ProgramController {

    private final MovieService movieService;
    private final OrderService orderService;

    @Autowired
    public ProgramController(MovieService movieService, OrderService orderService) {
        this.movieService = movieService;

        this.orderService = orderService;
    }

    @ModelAttribute("bookingTimes")
    public BookingTimeDto initBookingTimes() {
        return new BookingTimeDto();
    }

    @ModelAttribute("createOrder")
    public OrderMovieDto initNewOrder() {
        return new OrderMovieDto();
    }

    @GetMapping
    public String allMoviesInProgram(Model model) {
        Set<MovieViewDto> allMoviesView = this.movieService.getAllMoviesView();
        model.addAttribute("allViewMovies", allMoviesView);
        return "program";
    }

    @GetMapping("/order-tickets")
    public String getAllMoviesWithBookingTimes(Model model,
                                               @AuthenticationPrincipal UserDetails userDetails) {

        Set<MovieViewDto> allMoviesWithBookingTime = this.movieService.getAllMoviesViewWithBookingTimes();
        model.addAttribute("allViewMovies", allMoviesWithBookingTime);

        OrderMovieDto orderMovieDto = this.orderService.getUnfinishedOrderByUser(userDetails.getUsername());
        model.addAttribute("orderMovie", orderMovieDto);

        return "order-tickets";
    }

    @PostMapping
    public String createOrder(@Valid OrderMovieDto createOrder,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttr,
                              @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return Constant.REDIRECT_LOGIN;
        }
        if (bindingResult.hasErrors()) {
            redirectAttr
                    .addFlashAttribute(Constant.ORDER_ATTRIBUTE_NAME, createOrder)
                    .addFlashAttribute(Constant.ORDER_BINDING_RESULT_NAME, bindingResult);
            return Constant.REDIRECT_PROGRAM;
        }
        this.orderService.createUserOrder(createOrder, userDetails.getUsername());
        return Constant.REDIRECT_PROGRAM_ORDER_TICKETS;
    }

    @DeleteMapping("/order-tickets")
    public String cancelOrder() {
        this.orderService.deleteAllNotFinishedOrders();
        return Constant.REDIRECT_PROGRAM;
    }

    @GetMapping("/update-projection-time/{id}")
    public String updateProjection(@PathVariable long id, Model model) {
        MovieViewDto movieView = this.movieService.getMovieViewById(id);
        model.addAttribute("movieView", movieView);

        return "update-projection-time";
    }

    @PutMapping("/update-projection-time/{id}")
    public String updateProjection(@PathVariable long id,
                                   @Valid BookingTimeDto bookingTimes,
                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Constant.REDIRECT_UPDATE_BOOKING_TIME;
        }
        this.movieService.addBookingTimes(id, bookingTimes);
        return Constant.REDIRECT_PROGRAM;
    }
}
