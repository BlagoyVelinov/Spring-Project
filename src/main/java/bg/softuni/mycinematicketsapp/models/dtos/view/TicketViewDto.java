package bg.softuni.mycinematicketsapp.models.dtos.view;

import bg.softuni.mycinematicketsapp.models.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public class TicketViewDto {
    private long id;
    private long userId;
    private String movieName;
    private String hallNumber;
    @NotNull
    private Integer numberOfSeat;
    @NotNull
    private Integer numberOfRow;
    private Double price;
    private LocalDate projectionDate;
    private TicketType ticketType;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime bookingTime;
    private String cityName;
    private String movieClassDescription;
    private boolean isFinished;

    public long getId() {
        return id;
    }

    public TicketViewDto setId(long id) {
        this.id = id;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public TicketViewDto setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getMovieName() {
        return movieName;
    }

    public TicketViewDto setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public String getHallNumber() {
        return hallNumber;
    }

    public TicketViewDto setHallNumber(String hallNumber) {
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

    public LocalTime getBookingTime() {
        return bookingTime;
    }

    public TicketViewDto setBookingTime(LocalTime bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    public String getCityName() {
        return cityName;
    }

    public TicketViewDto setCityName(String cityName) {
        this.cityName = cityName;
        return this;
    }

    public String getMovieClassDescription() {
        return movieClassDescription;
    }

    public TicketViewDto setMovieClassDescription(String movieClassDescription) {
        this.movieClassDescription = movieClassDescription;
        return this;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public TicketViewDto setFinished(boolean finished) {
        isFinished = finished;
        return this;
    }
}
