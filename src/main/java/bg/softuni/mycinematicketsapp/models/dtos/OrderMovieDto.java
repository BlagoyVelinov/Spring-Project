package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.models.enums.CityName;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderMovieDto {

    private long id;
    private String movieViewName;
    private String orderNumber;

    private int ticketsQuantity;

    private String bookingTime;
    private List<TicketViewDto> tickets;
    @NotNull(message = "You need select a projection date.")
    @FutureOrPresent(message = "The date cannot be in the past!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate projectionDate;
    @NotNull(message = "You need select a city")
    private CityName location;


    public OrderMovieDto() {
        this.tickets = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public OrderMovieDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getMovieViewName() {
        return movieViewName;
    }

    public OrderMovieDto setMovieViewName(String movieViewName) {
        this.movieViewName = movieViewName;
        return this;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public OrderMovieDto setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public LocalDate getProjectionDate() {
        return projectionDate;
    }

    public OrderMovieDto setProjectionDate(LocalDate projectionDate) {
        this.projectionDate = projectionDate;
        return this;
    }

    public CityName getLocation() {
        return location;
    }

    public OrderMovieDto setLocation(CityName location) {
        this.location = location;
        return this;
    }

    public int getTicketsQuantity() {
        return ticketsQuantity;
    }

    public OrderMovieDto setTicketsQuantity(int ticketsQuantity) {
        this.ticketsQuantity = ticketsQuantity;
        return this;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public OrderMovieDto setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    public List<TicketViewDto> getTickets() {
        return tickets;
    }

    public OrderMovieDto setTickets(List<TicketViewDto> tickets) {
        this.tickets = tickets;
        return this;
    }
}
