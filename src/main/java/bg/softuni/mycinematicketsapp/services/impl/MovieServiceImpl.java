package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.entities.BookingTime;
import bg.softuni.mycinematicketsapp.services.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);
    private String BASE_URL = "http://localhost:8081/api/movies";
    private final RestClient moviesRestClient;

    @Autowired
    public MovieServiceImpl(@Qualifier("moviesRestClient") RestClient moviesRestClient) {
        this.moviesRestClient = moviesRestClient;
    }

    @Override
    public void movieCreate(CreateMovieDto createMovie) {
        LOGGER.info("Creating new movie...->");

        this.moviesRestClient.post()
                .uri(BASE_URL + "/add-movie")
                .body(createMovie)
                .retrieve();
    }

    @Override
    public Set<MovieViewDto> getAllMoviesView() {
        LOGGER.info("getAllMovies...->");

        return this.moviesRestClient.get()
                .uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public Set<MovieViewDto> getAllMoviesViewWithBookingTimes() {
        Set<MovieViewDto> movies = this.getAllMoviesView();
        return movies.stream()
                .filter(movie -> !movie.getBookingTimes().isEmpty())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<MovieViewDto> getAllMoviesViewWithoutBookingTimes() {
        return this.moviesRestClient.get()
                .uri(BASE_URL + "/upcoming")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>(){});
    }

    @Override
    public void addBookingTimes(long movieId, BookingTimeDto bookingTimeDto) {
        LOGGER.info("addBookingTimes...->");

        this.moviesRestClient.put()
                .uri(BASE_URL + "/update-projection-time/{id}", movieId)
                .body(bookingTimeDto)
                .retrieve();
    }
    @Override
    public BookingTimeDto getBookingTimeById(long timeId) {
        BookingTime bookingTime = this.moviesRestClient.get()
                .uri(BASE_URL + "/booking-time/{id}", timeId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(BookingTime.class);

        BookingTimeDto bookingTimeDto = new BookingTimeDto()
                .setBookingTimeValue(bookingTime.getBookingTime().getValue())
                .setId(bookingTime.getId());
        return bookingTimeDto;
    }

    @Override
    public void deleteMovieById(long movieId) {
        LOGGER.info("deleteMovieById...->");
        this.moviesRestClient.delete()
                .uri(BASE_URL + "/delete-movie/{id}", movieId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();
    }

    @Override
    public MovieViewDto getMovieViewById(long movieId) {
        LOGGER.info("getMovieById...->");

        return this.moviesRestClient.get()
                .uri(BASE_URL + "/movie/{id}", movieId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(MovieViewDto.class);
    }

}
