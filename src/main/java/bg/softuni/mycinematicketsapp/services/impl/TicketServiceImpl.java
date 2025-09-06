package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.repository.TicketRepository;
import bg.softuni.mycinematicketsapp.services.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TicketViewDto> getCurrentTickets() {
        return null;
    }

    @Override
    public void saveTicketsForOrder(Order order) {
        List<Ticket> tickets = order.getTickets();

        this.ticketRepository.saveAll(tickets);
    }

    private TicketViewDto mapTicketToTicketViewDto(Ticket ticket) {
        return this.modelMapper.map(ticket, TicketViewDto.class);
    }
}
