package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.HallNumber;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "projection_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate projectionDate;
    @Column(name = "child_quantity")
    private int childQuantity;
    @Column(name = "overSixty_quantity")
    private int overSixtyQuantity;
    @Column(name = "regular_quantity")
    private int regularQuantity;
    @Column(name = "student_quantity")
    private int studentQuantity;
    @Column(name = "is_finished")
    private boolean isFinished;
    @Column(name = "movie_name")
    private String movieName;
    @Column(name = "booking_time")
    private String bookingTime;
    @Column(name = "hall_number", nullable = false)
    private String hallNumber;
    @Column(name = "movie_id")
    private long movieId;
    @Column(name = "booking_time_id")
    private long bookingTimeId;
    @ManyToOne
    private City city;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "orders_tickets",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private List<Ticket> tickets;
    @ManyToOne
    private UserEntity user;

    public Order() {
        this.tickets = new ArrayList<>();
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Order setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getProjectionDate() {
        return projectionDate;
    }

    public Order setProjectionDate(LocalDate projectionDate) {
        this.projectionDate = projectionDate;
        return this;
    }

    public int getChildQuantity() {
        return childQuantity;
    }

    public void setChildQuantity(int childQuantity) {
        this.childQuantity = childQuantity;
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

    public int getStudentQuantity() {
        return studentQuantity;
    }

    public Order setStudentQuantity(int studentQuantity) {
        this.studentQuantity = studentQuantity;
        return this;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Order setFinished(boolean finished) {
        isFinished = finished;
        return this;
    }

    public String getMovieName() {
        return movieName;
    }

    public Order setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public City getCity() {
        return city;
    }

    public Order setCity(City city) {
        this.city = city;
        return this;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public Order setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    public String getHallNumber() {
        return hallNumber;
    }

    public Order setHallNumber(String hallNumber) {
        this.hallNumber = hallNumber;
        return this;
    }

    public long getMovieId() {
        return movieId;
    }

    public Order setMovieId(long movieId) {
        this.movieId = movieId;
        return this;
    }

    public long getBookingTimeId() {
        return bookingTimeId;
    }

    public Order setBookingTimeId(long bookingTimeId) {
        this.bookingTimeId = bookingTimeId;
        return this;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Order setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public UserEntity getUser() {
        return user;
    }

    public Order setUser(UserEntity user) {
        this.user = user;
        return this;
    }

}
