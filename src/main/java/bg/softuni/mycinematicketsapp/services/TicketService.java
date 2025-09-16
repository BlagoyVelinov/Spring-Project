package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;

import java.util.List;

public interface TicketService {
    void saveTicketsForOrder(Order order);

    List<Ticket> getAllTicketsByUser(long userId);

    List<TicketViewDto> getUpcomingTickets(long userId);

    List<TicketViewDto> getExpiredTickets(long userId);

    Ticket getTicket(long ticketId);
    TicketViewDto getTicketDto(long ticketId);

    String deleteFinishedTicket(long ticketId);
}
