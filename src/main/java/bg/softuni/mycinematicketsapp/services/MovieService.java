package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.MovieViewDto;

import java.util.Set;

public interface MovieService {
    void movieCreate(CreateMovieDto createMovie);
    Set<MovieViewDto> getAllMoviesView();
    Set<MovieViewDto> getAllMoviesViewWithBookingTimes();
    Set<MovieViewDto> getAllMoviesViewWithoutBookingTimes();
    void addBookingTimes(long movieId, BookingTimeDto bookingTimeDto);
    void deleteMovieById(long movieId);
    BookingTimeDto getBookingTimeById(long timeId);
    MovieViewDto getMovieViewById(long movieId);
}
