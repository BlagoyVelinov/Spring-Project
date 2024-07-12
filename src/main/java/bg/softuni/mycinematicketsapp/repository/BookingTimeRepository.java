package bg.softuni.mycinematicketsapp.repository;

import bg.softuni.mycinematicketsapp.models.entities.BookingTime;
import bg.softuni.mycinematicketsapp.models.enums.BookingTimeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingTimeRepository extends JpaRepository<BookingTime, Long> {
    List<BookingTime> findAllByStartTimeIn(List<BookingTimeEnum> startMovieTimes);

    BookingTime findByStartTime(BookingTimeEnum timeName);

}
