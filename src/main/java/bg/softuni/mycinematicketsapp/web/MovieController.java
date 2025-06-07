package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.MovieViewDto;
import bg.softuni.mycinematicketsapp.services.MovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<Set<MovieViewDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMoviesView());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<Set<MovieViewDto>> getUpcomingMovies() {
        return ResponseEntity.ok(movieService.getAllMoviesViewWithoutBookingTimes());
    }

    @PostMapping("/add-movie")
    public ResponseEntity<?> postAddMovie(@Valid @RequestBody CreateMovieDto createMovie,
                                          BindingResult bindingResult) {

        if (!createMovie.getTrailerUrl().contains("youtube")) {
            bindingResult.addError(new FieldError(
                    "createMovie", "trailerUrl", "Trailer must be a YouTube link."));
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        movieService.movieCreate(createMovie);
        return ResponseEntity.ok("Movie created successfully");
    }

    @DeleteMapping("/delete-movie/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable long id) {
        movieService.deleteMovieById(id);
        return ResponseEntity.ok("Movie deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieViewDto> getMovieTrailer(@PathVariable long id) {
        return ResponseEntity.ok(movieService.getMovieViewById(id));
    }
}
