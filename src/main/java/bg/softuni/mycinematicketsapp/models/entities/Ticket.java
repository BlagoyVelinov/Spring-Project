package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.HallNumber;
import bg.softuni.mycinematicketsapp.models.enums.TicketType;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @Column(name = "movie_name", nullable = false)
    private String movieName;
    @Column(name = "hall_number", nullable = false)
    private HallNumber hallNumber;
    @Column(name = "number_of_seat")
    private Integer numberOfSeat;
    @Column(name = "number_of_row")
    private Integer numberOfRow;
    @Column(nullable = false)
    private Double price;
    @Column(name = "projection_date", nullable = false)
    private LocalDate projectionDate;
    @Column
    private String movieClassDescription;
    @Column(name = "booking_time")
    private String bookingTime;
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;
    @ManyToOne
    private City city;

    @Column(name ="is_finished")
    private boolean isFinished;

    public String getMovieName() {
        return movieName;
    }

    public Ticket setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public HallNumber getHallNumber() {
        return hallNumber;
    }

    public Ticket setHallNumber(HallNumber hallNumber) {
        this.hallNumber = hallNumber;
        return this;
    }

    public Integer getNumberOfSeat() {
        return numberOfSeat;
    }

    public Ticket setNumberOfSeat(Integer numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
        return this;
    }

    public Integer getNumberOfRow() {
        return numberOfRow;
    }

    public Ticket setNumberOfRow(Integer numberOfRow) {
        this.numberOfRow = numberOfRow;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Ticket setPrice(Double price) {
        this.price = price;
        return this;
    }

    public LocalDate getProjectionDate() {
        return projectionDate;
    }

    public Ticket setProjectionDate(LocalDate projectionDate) {
        this.projectionDate = projectionDate;
        return this;
    }

    public String getMovieClassDescription() {
        return movieClassDescription;
    }

    public Ticket setMovieClassDescription(String movieClassDescription) {
        this.movieClassDescription = movieClassDescription;
        return this;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public Ticket setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public Ticket setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
        return this;
    }


    public City getCity() {
        return city;
    }

    public Ticket setCity(City city) {
        this.city = city;
        return this;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public Ticket setFinished(boolean finished) {
        isFinished = finished;
        return this;
    }
}
