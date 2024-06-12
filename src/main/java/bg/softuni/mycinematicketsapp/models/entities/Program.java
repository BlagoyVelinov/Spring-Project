package bg.softuni.mycinematicketsapp.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "programs")
public class Program extends BaseEntity{
    @Column(name = "date_of_projection", nullable = false)
    private LocalDate dateOfProjection;
    @OneToMany
    private Set<Movie> movies;

    public Program() {
        this.movies = new HashSet<>();
    }

    public LocalDate getDateOfProjection() {
        return dateOfProjection;
    }

    public Program setDateOfProjection(LocalDate dateOfProjection) {
        this.dateOfProjection = dateOfProjection;
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
