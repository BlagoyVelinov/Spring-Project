package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.ProgramViewDto;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.ProgramService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/program")
public class ProgramController {

    private final MovieService movieService;
    private final ProgramService programService;

    @Autowired
    public ProgramController(MovieService movieService, ProgramService programService) {
        this.movieService = movieService;
        this.programService = programService;
    }


    @ModelAttribute("bookingTimes")
    public BookingTimeDto initBookingTimes() {
        return new BookingTimeDto();
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
