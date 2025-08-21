package bg.softuni.mycinematicketsapp.services;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;

import java.util.List;
import java.util.Map;

public interface TicketService {

    Map<String, List<TicketViewDto>> addToTicketsMap(long orderId);

    Map<Integer, List<Integer>> getSeatsByRow(long orderId);

    void confirmOrder(long orderId);

}
