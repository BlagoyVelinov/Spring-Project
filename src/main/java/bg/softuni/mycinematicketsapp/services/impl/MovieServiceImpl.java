package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.CreateMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Category;
import bg.softuni.mycinematicketsapp.models.entities.Movie;
import bg.softuni.mycinematicketsapp.models.enums.Genre;
import bg.softuni.mycinematicketsapp.repository.MovieRepository;
import bg.softuni.mycinematicketsapp.services.CategoryService;
import bg.softuni.mycinematicketsapp.services.MovieService;
import org.hibernate.ObjectNotFoundException;
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
    private final ModelMapper modelMapper;
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.movieRepository = movieRepository;
        this.categoryService = categoryService;
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
    public Set<MovieViewDto> getAllMoviesView() {
        List<Movie> movies = this.getAllMovies();
        return movies.stream().map(this::mapMovieToMovieViewDto)
                .collect(Collectors.toSet());
    }


    @Override
    public void movieCreate(CreateMovieDto createMovie) {
        List<Category> categories = this.categoryService.getCategoryByGenre(createMovie.getGenreCategories());
        Movie movie = this.modelMapper.map(createMovie, Movie.class);
        movie.setGenreCategories(categories);

        this.movieRepository.save(movie);
    }

    private MovieViewDto mapMovieToMovieViewDto(Movie movie) {
        return this.modelMapper.map(movie, MovieViewDto.class);
    }

}
