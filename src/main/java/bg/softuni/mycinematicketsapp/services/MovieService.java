package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Movie;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MovieService {
    void movieCreate(CreateMovieDto createMovie);

    List<Movie> getAllMovies();

    Movie getMovieById(long id);

    Set<MovieViewDto> getAllMoviesView();

    void addBookingTimes(long movieId, BookingTimeDto bookingTimeDto);
    void removeBookingTimes(long movieId, long bookingTimeId);

    MovieViewDto getMovieViewById(long movieId);
}
