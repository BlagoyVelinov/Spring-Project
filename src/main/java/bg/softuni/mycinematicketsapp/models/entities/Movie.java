package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.ProjectionFormat;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "movie_class_id")
    private MovieClass movieClass;
    @Column(name = "projection_format")
    @Enumerated(EnumType.STRING)
    private ProjectionFormat projectionFormat;
    @Column(name = "movie_length", nullable = false)
    private Integer movieLength;
    @Column
    private String audio;
    @Column
    private String subtitles;
    @Column(name = "start_movie_time")
    private String startMovieTime;
    @Column(name = "trailer_url", nullable = false)
    private String trailerUrl;
    @OneToMany
    @JoinTable(
            name = "movies_categories",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> genreCategories;

    public Movie() {
        this.genreCategories = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Movie setName(String name) {
        this.name = name;
        return this;
    }

    public MovieClass getMovieClass() {
        return movieClass;
    }

    public Movie setMovieClass(MovieClass movieClass) {
        this.movieClass = movieClass;
        return this;
    }

    public ProjectionFormat getProjectionFormat() {
        return projectionFormat;
    }

    public Movie setProjectionFormat(ProjectionFormat projectionFormat) {
        this.projectionFormat = projectionFormat;
        return this;
    }

    public Integer getMovieLength() {
        return movieLength;
    }

    public Movie setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    public Movie setAudio(String audio) {
        this.audio = audio;
        return this;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public Movie setSubtitles(String subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public String getStartMovieTime() {
        return startMovieTime;
    }

    public Movie setStartMovieTime(String startMovieTime) {
        this.startMovieTime = startMovieTime;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public Movie setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    public List<Category> getGenreCategories() {
        return genreCategories;
    }

    public Movie setGenreCategories(List<Category> genreCategories) {
        this.genreCategories = genreCategories;
        return this;
    }
}
