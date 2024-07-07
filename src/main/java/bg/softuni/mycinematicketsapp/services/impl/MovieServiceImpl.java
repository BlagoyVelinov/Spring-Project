package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.entities.BookingTime;
import bg.softuni.mycinematicketsapp.models.entities.Category;
import bg.softuni.mycinematicketsapp.models.entities.Movie;
import bg.softuni.mycinematicketsapp.models.enums.Genre;
import bg.softuni.mycinematicketsapp.repository.MovieRepository;
import bg.softuni.mycinematicketsapp.services.BookingTimeService;
import bg.softuni.mycinematicketsapp.services.CategoryService;
import bg.softuni.mycinematicketsapp.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final CategoryService categoryService;
    private final BookingTimeService bookingTimeService;
    private final ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, CategoryService categoryService,
                            BookingTimeService bookingTimeService, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.categoryService = categoryService;
        this.bookingTimeService = bookingTimeService;
        this.modelMapper = modelMapper;
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
        List<Category> categories = this.categoryService.getCategoryByGenre(createMovie.getGenreCategories());
        Movie movie = this.modelMapper.map(createMovie, Movie.class);
        movie.setGenreCategories(categories);

        this.movieRepository.save(movie);
    }

    @Override
    public Set<MovieViewDto> getAllMoviesView() {
        List<Movie> movies = this.getAllMovies();
        return movies.stream().map(this::mapMovieToMovieViewDto)
                .collect(Collectors.toSet());
    }

    @Override
    public void addBookingTimes(long movieId, BookingTimeDto bookingTimeDto) {
        Movie movie = this.getMovieById(movieId);
        List<BookingTime> bookingTimes = this.bookingTimeService.getBookingTimesByStartTime(bookingTimeDto);
        movie.setBookingTimes(bookingTimes);
    }

    @Override
    public void removeBookingTimes(long movieId, long bookingTimeId) {
        Movie movie = this.getMovieById(movieId);
        BookingTime bookingTime = this.bookingTimeService.getBookingTimeById(bookingTimeId);

        movie.getBookingTimes().remove(bookingTime);
        this.movieRepository.save(movie);
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
        return this.modelMapper.map(movie, MovieViewDto.class)
                .setGenreCategories(genreCategories);
    }

}
