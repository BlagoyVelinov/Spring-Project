package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.enums.HallNumber;
import bg.softuni.mycinematicketsapp.models.enums.TicketType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class TicketViewDto {
    private long id;
    private String movieName;
    private HallNumber hallNumber;
    @NotNull
    private Integer numberOfSeat;
    @NotNull
    private Integer numberOfRow;
    private Double price;
    private LocalDateTime projectionDate;
    private TicketType ticketType;
    private String bookingTime;
    private String city;
    private String movieClass;

    public long getId() {
        return id;
    }

    public TicketViewDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getMovieName() {
        return movieName;
    }

    public TicketViewDto setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public HallNumber getHallNumber() {
        return hallNumber;
    }

    public TicketViewDto setHallNumber(HallNumber hallNumber) {
        this.hallNumber = hallNumber;
        return this;
    }

    public Integer getNumberOfSeat() {
        return numberOfSeat;
    }

    public TicketViewDto setNumberOfSeat(Integer numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
        return this;
    }

    public Integer getNumberOfRow() {
        return numberOfRow;
    }

    public TicketViewDto setNumberOfRow(Integer numberOfRow) {
        this.numberOfRow = numberOfRow;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public TicketViewDto setPrice(Double price) {
        this.price = price;
        return this;
    }

    public LocalDateTime getProjectionDate() {
        return projectionDate;
    }

    public TicketViewDto setProjectionDate(LocalDateTime projectionDate) {
        this.projectionDate = projectionDate;
        return this;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public TicketViewDto setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
        return this;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public TicketViewDto setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    public String getCity() {
        return city;
    }

    public TicketViewDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getMovieClass() {
        return movieClass;
    }

    public TicketViewDto setMovieClass(String movieClass) {
        this.movieClass = movieClass;
        return this;
    }
}
