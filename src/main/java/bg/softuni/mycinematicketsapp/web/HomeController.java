package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private final MovieService movieService;
    private final OrderService orderService;
    @Autowired
    public HomeController(MovieService movieService, OrderService orderService) {
        this.movieService = movieService;
        this.orderService = orderService;
    }


    @GetMapping("/")
    public String index(Model model) {
        Set<MovieViewDto> allMoviesView = this.movieService.getAllMoviesView();
        model.addAttribute("allViewMovies", allMoviesView);
        return "index";
    }


    @GetMapping("/about-us")
    public String aboutUs() {
        return "about-us";
    }

    @GetMapping("/contact-us")
    public String contactUs() {
        return "contact-us";
    }

    @GetMapping("/4-dx")
    public String fourDx() {
        return "4-dx";
    }
    @GetMapping("/imax")
    public String imax() {
        return "imax";
    }

    @GetMapping("/trailer/{id}")
    public String trailer(@PathVariable long id, Model model) {
        MovieViewDto movieView = this.movieService.getMovieViewById(id);
        model.addAttribute("movie", movieView);
        return "trailer";
    }
//    @GetMapping("/program/order-tickets")
//    public String getAllMoviesWithBookingTimes(Model model,
//                                               @AuthenticationPrincipal UserDetails userDetails) {
//
//        Set<MovieViewDto> allMoviesWithBookingTime = this.movieService.getAllMoviesViewWithBookingTimes();
//        model.addAttribute("allViewMovies", allMoviesWithBookingTime);
//
//        OrderMovieDto orderMovieDto = this.orderService.getUnfinishedOrderByUser(userDetails.getUsername());
//        model.addAttribute("orderMovie", orderMovieDto);
//        return "order-tickets";
//    }

}
