package bg.softuni.mycinematicketsapp.models.entities;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "order_number")
    private String orderNumber;
    @Column(name = "total_price")
    private Double totalPrice;
    @Column(name = "projection_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate projectionDate;
    @Column(name = "tickets_quantity")
    private int ticketsQuantity;
    @Column(name = "is_finished")
    private boolean isFinished;
    @Column
    private String movieName;
    @Column(name = "booking_time")
    private String bookingTime;
    @ManyToOne
    private City city;
    @OneToMany
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

    public Double getTotalPrice() {
        return totalPrice;
//        return this.tickets.stream().mapToDouble(Ticket::getPrice).sum();
    }

    public Order setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }

    public LocalDate getProjectionDate() {
        return projectionDate;
    }

    public Order setProjectionDate(LocalDate projectionDate) {
        this.projectionDate = projectionDate;
        return this;
    }

    public int getTicketsQuantity() {
        return ticketsQuantity;
    }

    public Order setTicketsQuantity(int ticketsQuantity) {
        this.ticketsQuantity = ticketsQuantity;
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
