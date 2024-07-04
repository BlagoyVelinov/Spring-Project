package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @ModelAttribute("createMovie")
    public CreateMovieDto initMovie() {
        return new CreateMovieDto();
    }

    @GetMapping("/add-movie")
    public String getAddMovie() {
        return "add-movie";
    }

    @PostMapping("/add-movie")
    public String postAddMovie(@Valid CreateMovieDto createMovie,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute(Constant.MOVIE_ATTRIBUTE_NAME, createMovie)
                    .addFlashAttribute(Constant.MOVIE_BINDING_RESULT_NAME, bindingResult);
            return Constant.REDIRECT_ADD_MOVIE;
        }

        this.movieService.movieCreate(createMovie);

        return Constant.REDIRECT_PROGRAM;
    }
}
