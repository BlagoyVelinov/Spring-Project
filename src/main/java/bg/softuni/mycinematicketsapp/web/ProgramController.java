package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/program")
public class ProgramController {

    private final MovieService movieService;

    @Autowired
    public ProgramController(MovieService movieService) {
        this.movieService = movieService;
    }

    @ModelAttribute("bookingTimes")
    public BookingTimeDto initBookingTimes() {
        return new BookingTimeDto();
    }



    //    @GetMapping("/program")
//    public String getProgram(Model model) {
//        Set<MovieViewDto> allMoviesView = this.movieService.getAllMoviesView();
//        model.addAttribute("allViewMovies", allMoviesView);
//        return "program";
//    }

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
