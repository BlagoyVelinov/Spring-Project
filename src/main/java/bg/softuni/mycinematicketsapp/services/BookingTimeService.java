package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.entities.BookingTime;

import java.util.List;

public interface BookingTimeService {
    void initStartProjectionTimesInDb();

    List<BookingTime> getBookingTimesByStartTime(BookingTimeDto bookingTimeDto);

    BookingTime getBookingTimeById(long bookingTimeId);
}
