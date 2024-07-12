package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;
import bg.softuni.mycinematicketsapp.services.BookingTimeService;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
public class HomeController {
    private final MovieService movieService;

    @Autowired
    public HomeController(MovieService movieService) {
        this.movieService = movieService;
    }


    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/program")
    public String allMoviesInProgram(Model model) {
        Set<MovieViewDto> allMoviesView = this.movieService.getAllMoviesView();
        model.addAttribute("allViewMovies", allMoviesView);
        return "program";
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

}
