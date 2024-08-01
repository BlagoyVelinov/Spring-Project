package bg.softuni.mycinematicketsapp.models.entities;

import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;

public class BookingTime extends BaseEntity {

    private BookingTimeEnum bookingTime;

    public BookingTime() {}

    public BookingTime(BookingTimeEnum bookingTime) {
        this.setBookingTime(bookingTime);
    }

    public BookingTimeEnum getBookingTime() {
        return bookingTime;
    }

    public BookingTime setBookingTime(BookingTimeEnum bookingTime) {
        this.bookingTime = bookingTime;
        return this;
    }
}
