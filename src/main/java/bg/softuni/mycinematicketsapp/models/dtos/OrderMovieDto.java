package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.enums.CityName;

import java.time.LocalDate;

public class OrderMovieDto {

    private long id;

    private MovieViewDto movieView;

    private LocalDate projectionDate;

    private CityName location;

    public long getId() {
        return id;
    }

    public OrderMovieDto setId(long id) {
        this.id = id;
        return this;
    }

    public MovieViewDto getMovieView() {
        return movieView;
    }

    public OrderMovieDto setMovieView(MovieViewDto movieView) {
        this.movieView = movieView;
        return this;
    }

    public LocalDate getProjectionDate() {
        return projectionDate;
    }

    public OrderMovieDto setProjectionDate(LocalDate projectionDate) {
        this.projectionDate = projectionDate;
        return this;
    }

    public CityName getLocation() {
        return location;
    }

    public OrderMovieDto setLocation(CityName location) {
        this.location = location;
        return this;
    }
}
