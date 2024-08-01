package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
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

        if (!createMovie.getTrailerUrl().contains("youtube")) {
            bindingResult.addError(new FieldError(
                    Constant.OBJECT_DIFFERENT_TRAILER_URL,
                    Constant.FIELD_TRAILER_URL,
                    Constant.DEFAULT_MESSAGE_TRAILER));
        }
        if (bindingResult.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute(Constant.MOVIE_ATTRIBUTE_NAME, createMovie)
                    .addFlashAttribute(Constant.MOVIE_BINDING_RESULT_NAME, bindingResult);
            return Constant.REDIRECT_ADD_MOVIE;
        }

        this.movieService.movieCreate(createMovie);

        return Constant.REDIRECT_PROGRAM;
    }

    @DeleteMapping("/delete-movie/{id}")
    public String deleteMovie(@PathVariable long id) {
        this.movieService.deleteMovieById(id);
        return Constant.REDIRECT_PROGRAM;
    }
}
