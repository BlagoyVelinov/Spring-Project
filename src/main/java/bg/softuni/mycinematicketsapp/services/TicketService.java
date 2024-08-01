package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.UpdateTicketDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;

import java.util.List;
import java.util.Map;

public interface TicketService {
    void createTickets(TicketViewDto createTicket, long orderId, long movieId);

    List<Ticket> getTicketsByOrder(Order order);

    void updateTickets(UpdateTicketDto updateTicket, long orderId, long movieId);

    Map<String, List<TicketViewDto>> addToTicketsMap(long orderId);

    Map<Integer, List<Integer>> getSeatsByRow(long orderId);

    void confirmOrder(long orderId);

}
