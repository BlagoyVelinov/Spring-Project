package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;
import bg.softuni.mycinematicketsapp.models.enums.TicketType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "movie_name")
    private String movieName;
    @Enumerated(EnumType.STRING)
    @Column(name = "booking_time")
    private BookingTimeEnum bookingTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type")
    private TicketType ticketType;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false)
    private Integer numberOfRow;
    @Column(nullable = false)
    private Integer numberOfSeat;
    @Column(name = "voucher_number")
    private String voucherNumber;

    @Column(name = "projection_date")
    private LocalDate projectionDate;
    @ManyToOne
    private UserEntity user;

    public String getMovieName() {
        return movieName;
    }

    public Order setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public BookingTimeEnum getBookingTime() {
        return bookingTime;
    }

    public Order setBookingTime(BookingTimeEnum bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public Order setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Order setPrice(Double price) {
        this.price = price;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Order setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getNumberOfRow() {
        return numberOfRow;
    }

    public Order setNumberOfRow(Integer numberOfRow) {
        this.numberOfRow = numberOfRow;
        return this;
    }

    public Integer getNumberOfSeat() {
        return numberOfSeat;
    }

    public Order setNumberOfSeat(Integer numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
        return this;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public Order setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
        return this;
    }

    public LocalDate getProjectionDate() {
        return projectionDate;
    }

    public Order setProjectionDate(LocalDate projectionDate) {
        this.projectionDate = projectionDate;
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
