package bg.softuni.mycinematicketsapp.models.dtos;

import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderMovieDto {

    private long id;
    private long movieId;
    private long bookingTimeId;
    private String movieViewName;
    private String orderNumber;
    private int childQuantity;
    private int overSixtyQuantity;
    private int regularQuantity;
    private int studentQuantity;
    private double totalPrice;
    private String bookingTime;
    private List<Ticket> tickets;
    @NotNull(message = "You need select a projection date.")
    @FutureOrPresent(message = "The date cannot be in the past!")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate projectionDate;

    @NotNull(message = "You need select a city")
    private CityName location;
    private UserViewDto user;


    private LocalDate startDate;
    private LocalDate endDate;

    public OrderMovieDto() {
        this.tickets = new ArrayList<>();
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusDays(Constant.COUNT_AVAILABLE_DAYS);
    }

    public long getId() {
        return id;
    }

    public OrderMovieDto setId(long id) {
        this.id = id;
        return this;
    }

    public long getMovieId() {
        return movieId;
    }

    public OrderMovieDto setMovieId(long movieId) {
        this.movieId = movieId;
        return this;
    }

    public long getBookingTimeId() {
        return bookingTimeId;
    }

    public OrderMovieDto setBookingTimeId(long bookingTimeId) {
        this.bookingTimeId = bookingTimeId;
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

    public int getChildQuantity() {
        return childQuantity;
    }

    public void setChildQuantity(int childQuantity) {
        this.childQuantity = childQuantity;
    }

    public int getStudentQuantity() {
        return studentQuantity;
    }

    public void setStudentQuantity(int studentQuantity) {
        this.studentQuantity = studentQuantity;
    }

    public int getOverSixtyQuantity() {
        return overSixtyQuantity;
    }

    public void setOverSixtyQuantity(int overSixtyQuantity) {
        this.overSixtyQuantity = overSixtyQuantity;
    }

    public int getRegularQuantity() {
        return regularQuantity;
    }

    public void setRegularQuantity(int regularQuantity) {
        this.regularQuantity = regularQuantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public OrderMovieDto setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public OrderMovieDto setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public UserViewDto getUser() {
        return user;
    }

    public OrderMovieDto setUser(UserViewDto user) {
        this.user = user;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public OrderMovieDto setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public OrderMovieDto setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
