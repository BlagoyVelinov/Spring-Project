package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.MovieViewDto;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/program")
public class ProgramController {

    private final MovieService movieService;
    private final OrderService orderService;

    @Autowired
    public ProgramController(MovieService movieService, OrderService orderService) {
        this.movieService = movieService;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> allMoviesInProgram() {
        Set<MovieViewDto> allMoviesView = this.movieService.getAllMoviesView();
        return ResponseEntity.ok(allMoviesView);
    }

    @GetMapping("/order-tickets")
    public ResponseEntity<?> getAllMoviesWithBookingTimes(@AuthenticationPrincipal UserDetails userDetails) {
        Set<MovieViewDto> allMoviesWithBookingTime = this.movieService.getAllMoviesViewWithBookingTimes();
        Map<String, Object> response = Map.of(
                "allViewMovies", allMoviesWithBookingTime,
                "orderMovie", this.orderService.getUnfinishedOrderByUser(userDetails.getUsername())
        );
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/order-tickets")
    public ResponseEntity<?> cancelOrder() {
        this.orderService.deleteAllNotFinishedOrders();
        return ResponseEntity.ok(Map.of("message", "All unfinished orders have been canceled"));
    }

    @GetMapping("/update-projection-time/{id}")
    public ResponseEntity<?> updateProjection(@PathVariable long id) {
        MovieViewDto movieView = this.movieService.getMovieViewById(id);
        return ResponseEntity.ok(movieView);
    }

    @PutMapping("/update-projection-time/{id}")
    public ResponseEntity<?> updateProjection(@PathVariable long id,
                                              @Valid @RequestBody BookingTimeDto bookingTimes) {
        this.movieService.addBookingTimes(id, bookingTimes);
        return ResponseEntity.ok(Map.of("message", "Projection time updated successfully"));
    }
}
