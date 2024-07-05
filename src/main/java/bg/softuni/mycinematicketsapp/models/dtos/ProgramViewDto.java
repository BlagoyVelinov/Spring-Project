package bg.softuni.mycinematicketsapp.models.dtos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ProgramViewDto {
    private LocalDate dateToday;
    private Set<MovieViewDto> movies;

    public ProgramViewDto() {

        this.movies = new HashSet<>();
    }

    public LocalDate getDateToday() {
        return dateToday;
    }

    public ProgramViewDto setDateToday(LocalDate dateToday) {
        this.dateToday = dateToday;
        return this;
    }

    public Set<MovieViewDto> getMovies() {
        return movies;
    }

    public ProgramViewDto setMovies(Set<MovieViewDto> movies) {
        this.movies = movies;
        return this;
    }
}
