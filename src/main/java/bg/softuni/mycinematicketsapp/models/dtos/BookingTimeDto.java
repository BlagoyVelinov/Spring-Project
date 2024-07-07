package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;

import java.util.ArrayList;
import java.util.List;

public class BookingTimeDto {
    private long id;
    private BookingTimeEnum startMovieTime;
    private List<BookingTimeEnum> startMovieTimes;

    public BookingTimeDto() {
        this.startMovieTimes = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public BookingTimeDto setId(long id) {
        this.id = id;
        return this;
    }

    public BookingTimeEnum getStartMovieTime() {
        return startMovieTime;
    }

    public BookingTimeDto setStartMovieTime(BookingTimeEnum startMovieTime) {
        this.startMovieTime = startMovieTime;
        return this;
    }

    public List<BookingTimeEnum> getStartMovieTimes() {
        return startMovieTimes;
    }

    public BookingTimeDto setStartMovieTimes(List<BookingTimeEnum> startMovieTimes) {
        this.startMovieTimes = startMovieTimes;
        return this;
    }
}
