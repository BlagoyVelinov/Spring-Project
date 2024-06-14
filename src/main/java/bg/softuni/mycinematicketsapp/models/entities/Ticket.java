package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.CityName;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @Column(name = "movie_name", nullable = false)
    private String movieName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CityName city;
    @Column(name = "number_of_room", nullable = false)
    private Integer numberRoom;
    @Column(name = "number_of_seat", nullable = false)
    private Integer numberOfSeat;
    @Column(name = "number_of_row", nullable = false)
    private Integer numberOfRow;
    @Column(nullable = false)
    private Double price;
    @Column(name = "projection_date", nullable = false)
    private LocalDateTime projectionDate;

    @OneToOne
    private MovieClass movieClass;

    public Ticket() {
    }

    public String getMovieName() {
        return movieName;
    }

    public Ticket setMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }

    public CityName getCity() {
        return city;
    }

    public Ticket setCity(CityName city) {
        this.city = city;
        return this;
    }

    public Integer getNumberRoom() {
        return numberRoom;
    }

    public Ticket setNumberRoom(Integer numberRoom) {
        this.numberRoom = numberRoom;
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

    public LocalDateTime getProjectionDate() {
        return projectionDate;
    }

    public Ticket setProjectionDate(LocalDateTime projectionDate) {
        this.projectionDate = projectionDate;
        return this;
    }

    public MovieClass getMovieClass() {
        return movieClass;
    }

    public Ticket setMovieClass(MovieClass movieClass) {
        this.movieClass = movieClass;
        return this;
    }
}
