package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.enums.Genre;
import bg.softuni.mycinematicketsapp.models.enums.MovieClassEnum;
import bg.softuni.mycinematicketsapp.models.enums.ProjectionFormat;
import bg.softuni.mycinematicketsapp.models.enums.StartProjectionTimeEnum;

import java.util.ArrayList;
import java.util.List;

public class MovieViewDto {

    private long id;
    private String name;
    private Integer movieLength;
    private String audio;
    private String subtitles;
    private String description;
    private String imageUrl;
    private String trailerUrl;
    private ProjectionFormat projectionFormat;
    private MovieClassEnum movieClass;
    private List<Genre> genreCategories;
    private List<StartProjectionTimeEnum> startProjectionTimeList;

    public MovieViewDto() {
        this.genreCategories = new ArrayList<>();
        this.startProjectionTimeList = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public MovieViewDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MovieViewDto setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getMovieLength() {
        return movieLength;
    }

    public MovieViewDto setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
        return this;
    }

    public String getAudio() {
        return audio;
    }

    public MovieViewDto setAudio(String audio) {
        this.audio = audio;
        return this;
    }

    public String getSubtitles() {
        return subtitles;
    }

    public MovieViewDto setSubtitles(String subtitles) {
        this.subtitles = subtitles;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MovieViewDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MovieViewDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public MovieViewDto setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
        return this;
    }

    public ProjectionFormat getProjectionFormat() {
        return projectionFormat;
    }

    public MovieViewDto setProjectionFormat(ProjectionFormat projectionFormat) {
        this.projectionFormat = projectionFormat;
        return this;
    }

    public MovieClassEnum getMovieClass() {
        return movieClass;
    }

    public MovieViewDto setMovieClass(MovieClassEnum movieClass) {
        this.movieClass = movieClass;
        return this;
    }

    public List<Genre> getGenreCategories() {
        return genreCategories;
    }

    public MovieViewDto setGenreCategories(List<Genre> genreCategories) {
        this.genreCategories = genreCategories;
        return this;
    }

    public List<StartProjectionTimeEnum> getStartProjectionTimeList() {
        return startProjectionTimeList;
    }

    public MovieViewDto setStartProjectionTimeList(List<StartProjectionTimeEnum> startProjectionTimeList) {
        this.startProjectionTimeList = startProjectionTimeList;
        return this;
    }
}
