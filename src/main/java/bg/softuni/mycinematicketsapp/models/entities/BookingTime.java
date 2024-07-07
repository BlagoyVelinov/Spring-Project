package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_times")
public class BookingTime extends BaseEntity {

    private BookingTimeEnum startTime;

    public BookingTime() {}

    public BookingTime(BookingTimeEnum startTime) {
        this.startTime = startTime;
    }

    public BookingTimeEnum getStartTime() {
        return startTime;
    }

    public BookingTime setStartTime(BookingTimeEnum startTime) {
        this.startTime = startTime;
        return this;
    }
}
