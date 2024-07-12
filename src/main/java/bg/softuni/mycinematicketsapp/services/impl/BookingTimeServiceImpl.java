package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.entities.BookingTime;
import bg.softuni.mycinematicketsapp.models.entities.MovieClass;
import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;
import bg.softuni.mycinematicketsapp.models.enums.MovieClassEnum;
import bg.softuni.mycinematicketsapp.repository.BookingTimeRepository;
import bg.softuni.mycinematicketsapp.services.BookingTimeService;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BookingTimeServiceImpl implements BookingTimeService {
    private final BookingTimeRepository bookingTimeRepository;

    @Autowired
    public BookingTimeServiceImpl(BookingTimeRepository bookingTimeRepository) {
        this.bookingTimeRepository = bookingTimeRepository;
    }

    @Override
    public void initStartProjectionTimesInDb() {
        if (this.bookingTimeRepository.count() == 0) {
            List<BookingTime> bookingTimeList = Arrays.stream(BookingTimeEnum.values())
                    .map(BookingTime::new)
                    .toList();

            this.bookingTimeRepository.saveAll(bookingTimeList);
        }
    }

    @Override
    public List<BookingTime> getBookingTimesByStartTime(BookingTimeDto bookingTimeDto) {
        return this.bookingTimeRepository.findAllByStartTimeIn(bookingTimeDto.getStartMovieTimes());
    }

    @Override
    public BookingTime getBookingTimeById(long bookingTimeId) {
        return this.bookingTimeRepository.findById(bookingTimeId)
                .orElseThrow(() -> new ObjectNotFoundException("Booking time is not found!"));
    }

    @Override
    public BookingTimeEnum getBookingTimeEnumById(long timeId) {
        BookingTime byStartTime = this.getBookingTimeById(timeId);
        return byStartTime.getStartTime();
    }
}
