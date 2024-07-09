package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_times")
public class BookingTime extends BaseEntity {

    @Column(name = "start_time")
    private BookingTimeEnum startTime;

    @Column(name = "booking_time")
    private String bookingTime;

    public BookingTime() {}

    public BookingTime(BookingTimeEnum startTime) {
        this.setStartTime(startTime);
    }

    public BookingTimeEnum getStartTime() {
        return startTime;
    }

    public BookingTime setStartTime(BookingTimeEnum startTime) {
        this.startTime = startTime;
        this.setBookingTime(startTime);
        return this;
    }

    public String getBookingTime() {
        return bookingTime;
    }

    public BookingTime setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }

    private void setBookingTime(BookingTimeEnum startTime) {
        String bookingTime = "";
        switch (startTime) {
            case _10_20 -> bookingTime = BookingTimeEnum._10_20.getValue();
            case _11_50 -> bookingTime = BookingTimeEnum._11_50.getValue();
            case _12_20 -> bookingTime = BookingTimeEnum._12_20.getValue();
            case _13_50 -> bookingTime = BookingTimeEnum._13_50.getValue();
            case _14_20 -> bookingTime = BookingTimeEnum._14_20.getValue();
            case _15_50 -> bookingTime = BookingTimeEnum._15_50.getValue();
            case _16_20 -> bookingTime = BookingTimeEnum._16_20.getValue();
            case _17_50 -> bookingTime = BookingTimeEnum._17_50.getValue();
            case _18_20 -> bookingTime = BookingTimeEnum._18_20.getValue();
            case _19_50 -> bookingTime = BookingTimeEnum._19_50.getValue();
            case _20_20 -> bookingTime = BookingTimeEnum._20_20.getValue();
            case _20_50 -> bookingTime = BookingTimeEnum._20_50.getValue();
        }
        this.bookingTime = bookingTime;
    }
}
