package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.entities.*;
import bg.softuni.mycinematicketsapp.models.enums.Genre;
import bg.softuni.mycinematicketsapp.repository.MovieRepository;
import bg.softuni.mycinematicketsapp.services.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private Logger LOGGER = LoggerFactory.getLogger(MovieServiceImpl.class);
    private final MovieRepository movieRepository;
    private final RestClient moviesRestClient;
    private final CategoryService categoryService;
    private final BookingTimeService bookingTimeService;
    private final ModelMapper modelMapper;
    private final MovieClassServiceImpl movieClassService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, @Qualifier("moviesRestClient") RestClient moviesRestClient,
                            CategoryService categoryService, BookingTimeService bookingTimeService, ModelMapper modelMapper,
                            MovieClassServiceImpl movieClassService) {
        this.movieRepository = movieRepository;
        this.moviesRestClient = moviesRestClient;
        this.categoryService = categoryService;
        this.bookingTimeService = bookingTimeService;
        this.modelMapper = modelMapper;
        this.movieClassService = movieClassService;
    }

    @Override
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(long id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie is not found!"));
    }

    @Override
    public void movieCreate(CreateMovieDto createMovie) {
//        List<Category> categories = this.categoryService.getCategoryByGenre(createMovie.getGenreCategories());
//        MovieClass movieClass = this.movieClassService.getMovieClassByName(createMovie.getMovieClass());
//        Movie movie = this.modelMapper.map(createMovie, Movie.class);
//        movie.setGenreCategories(categories)
//                .setMovieClass(movieClass);
//
//        this.movieRepository.save(movie);
        LOGGER.info("Creating new movie...->");
        this.moviesRestClient.post()
                .uri("http://localhost:8081/movies/add-movie")
                .body(createMovie)
                .retrieve();
    }

    @Override
    public Set<MovieViewDto> getAllMoviesView() {
        List<Movie> movies = this.getAllMovies();
//        return movies.stream()
//                .map(this::mapMovieToMovieViewDto)
//                .collect(Collectors.toSet());

        return this.moviesRestClient.get()
                .uri("http://localhost:8081/movies")
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
    public void addBookingTimes(long movieId, BookingTimeDto bookingTimeDto) {
        Movie movie = this.getMovieById(movieId);
        List<BookingTime> bookingTimes = this.bookingTimeService.getBookingTimesByStartTime(bookingTimeDto);
        movie.setBookingTimes(bookingTimes);
        this.movieRepository.save(movie);
    }

    @Override
    public void deleteMovieById(long movieId) {
        Movie movie = this.getMovieById(movieId);
        List<BookingTime> bookingTimes = movie.getBookingTimes();
        List<Category> genreCategories = movie.getGenreCategories();
        movie.getGenreCategories().removeAll(genreCategories);
        movie.getBookingTimes().removeAll(bookingTimes);
        this.movieRepository.deleteById(movieId);
    }

    @Override
    public MovieViewDto getMovieViewById(long movieId) {
        Movie movie = this.getMovieById(movieId);
        return this.mapMovieToMovieViewDto(movie);
    }

    private MovieViewDto mapMovieToMovieViewDto(Movie movie) {
        List<Genre> genreCategories = movie.getGenreCategories()
                .stream().map(Category::getName)
                .toList();
        MovieClass movieClass = movie.getMovieClass();
        return this.modelMapper.map(movie, MovieViewDto.class)
                .setGenreCategories(genreCategories)
                .setMovieClass(movieClass);
    }

}
