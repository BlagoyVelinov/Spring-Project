package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;

import java.util.List;

public interface TicketService {

    List<TicketViewDto> getCurrentTickets();
    void saveTicketsForOrder(Order order);

    List<TicketViewDto> getUpcomingTickets(long userId);

    List<TicketViewDto> getExpiredTickets(long userId);
}
