package bg.softuni.mycinematicketsapp.repository;

import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
