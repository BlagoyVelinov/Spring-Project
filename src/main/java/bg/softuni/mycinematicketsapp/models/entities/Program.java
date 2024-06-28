package bg.softuni.mycinematicketsapp.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "programs")
public class Program extends BaseEntity{
    @Column(name = "date_of_projection_start", nullable = false)
    private LocalDate dateOfProjectionStart;
    @Column(name = "date_of_projection_end", nullable = false)
    private LocalDate dateOfProjectionEnd;
    @Column(name = "start_projection_time")
    private LocalTime startProjectionTime;
    @OneToMany
    private Set<Movie> movies;

    public Program() {
        this.movies = new HashSet<>();
    }

    public LocalDate getDateOfProjectionStart() {
        return dateOfProjectionStart;
    }

    public Program setDateOfProjectionStart(LocalDate dateOfProjectionStart) {
        this.dateOfProjectionStart = dateOfProjectionStart;
        return this;
    }

    public LocalDate getDateOfProjectionEnd() {
        return dateOfProjectionEnd;
    }

    public Program setDateOfProjectionEnd(LocalDate dateOfProjectionEnd) {
        this.dateOfProjectionEnd = dateOfProjectionEnd;
        return this;
    }

    public LocalTime getStartProjectionTime() {
        return startProjectionTime;
    }

    public Program setStartProjectionTime(LocalTime startProjectionTime) {
        this.startProjectionTime = startProjectionTime;
        return this;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public Program setMovies(Set<Movie> movies) {
        this.movies = movies;
        return this;
    }
}
