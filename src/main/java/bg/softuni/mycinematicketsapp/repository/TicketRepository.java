package bg.softuni.mycinematicketsapp.repository;

import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByUserIdAndFinishedIsFalseOrderByProjectionDate(long userId);

    List<Ticket> findAllByUserIdAndFinishedIsTrueOrderByProjectionDate(long userId);

    @Modifying
    @Transactional
    @Query("UPDATE Ticket t " +
            "SET t.finished = true " +
            "WHERE t.finished = false " +
            "AND (t.projectionDate < CURRENT_DATE " +
            "OR (t.projectionDate = CURRENT_DATE AND t.bookingTime <= :currentTime))")
    int markFinishedTickets(@Param("currentTime") LocalTime currentTime);
}
