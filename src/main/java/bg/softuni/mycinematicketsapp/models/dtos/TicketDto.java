package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.entities.BookingTime;
import bg.softuni.mycinematicketsapp.models.entities.City;
import bg.softuni.mycinematicketsapp.models.entities.MovieClass;
import bg.softuni.mycinematicketsapp.models.enums.HallNumber;
import bg.softuni.mycinematicketsapp.models.enums.TicketType;

import java.time.LocalDateTime;

public class TicketDto {
    private long id;
    private String movieName;
    private HallNumber hallNumber;
    private Integer numberOfSeat;
    private Integer numberOfRow;
    private Double price;
    private LocalDateTime projectionDate;
    private TicketType ticketType;
    private BookingTime bookingTime;
    private String city;
    private String movieClass;

    public long getId() {
        return id;
    }

    public TicketDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getMovieName() {
        return movieName;
    }

    public TicketDto setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public HallNumber getHallNumber() {
        return hallNumber;
    }

    public TicketDto setHallNumber(HallNumber hallNumber) {
        this.hallNumber = hallNumber;
        return this;
    }

    public Integer getNumberOfSeat() {
        return numberOfSeat;
    }

    public TicketDto setNumberOfSeat(Integer numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
        return this;
    }

    public Integer getNumberOfRow() {
        return numberOfRow;
    }

    public TicketDto setNumberOfRow(Integer numberOfRow) {
        this.numberOfRow = numberOfRow;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public TicketDto setPrice(Double price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getProjectionDate() {
        return projectionDate;
    }

    public TicketDto setProjectionDate(LocalDateTime projectionDate) {
        this.projectionDate = projectionDate;
        return this;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public TicketDto setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
        return this;
    }

    public BookingTime getBookingTime() {
        return bookingTime;
    }

    public TicketDto setBookingTime(BookingTime bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    public String getCity() {
        return city;
    }

    public TicketDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getMovieClass() {
        return movieClass;
    }

    public TicketDto setMovieClass(String movieClass) {
        this.movieClass = movieClass;
        return this;
    }
}
