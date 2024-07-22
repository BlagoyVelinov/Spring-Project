package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;

import java.util.List;

public interface TicketService {
    void createTickets(TicketViewDto createTicket, long orderId, long movieId);

    List<Ticket> getTicketsByOrder(Order order);
}
