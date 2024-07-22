package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.entities.BookingTime;
import bg.softuni.mycinematicketsapp.models.entities.Movie;

import java.util.List;
import java.util.Set;

public interface MovieService {
    void movieCreate(CreateMovieDto createMovie);
    Set<MovieViewDto> getAllMoviesView();
    Set<MovieViewDto> getAllMoviesViewWithBookingTimes();
    void addBookingTimes(long movieId, BookingTimeDto bookingTimeDto);
    void deleteMovieById(long movieId);
    BookingTime getBookingTimeById(long timeId);
    MovieViewDto getMovieViewById(long movieId);
}
