package bg.softuni.mycinematicketsapp.models.dtos.view;

import bg.softuni.mycinematicketsapp.models.enums.HallNumber;
import bg.softuni.mycinematicketsapp.models.enums.TicketType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class TicketViewDto {
    private long id;
    private String movieName;
    private HallNumber hallNumber;
    @NotNull
    private Integer numberOfSeat;
    @NotNull
    private Integer numberOfRow;
    private Double price;
    private LocalDate projectionDate;
    private TicketType ticketType;
    private String bookingTime;
    private String city;
    private String movieClassDescription;

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

    public LocalDate getProjectionDate() {
        return projectionDate;
    }

    public TicketViewDto setProjectionDate(LocalDate projectionDate) {
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

    public String getMovieClassDescription() {
        return movieClassDescription;
    }

    public TicketViewDto setMovieClassDescription(String movieClassDescription) {
        this.movieClassDescription = movieClassDescription;
        return this;
    }
}
