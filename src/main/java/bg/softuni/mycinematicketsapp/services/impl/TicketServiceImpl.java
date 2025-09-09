package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.constants.ExceptionMessages;
import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.repository.TicketRepository;
import bg.softuni.mycinematicketsapp.services.TicketService;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public void saveTicketsForOrder(Order order) {
        List<Ticket> tickets = order.getTickets();

        this.ticketRepository.saveAll(tickets);
    }


    @Override
    public List<TicketViewDto> getUpcomingTickets(long userId) {

        List<Ticket> userTickets = this.ticketRepository.findAllByUserId(userId)
                .stream()
                .filter(ticket -> !ticket.isFinished())
                .toList();

        return userTickets.stream()
                .map(this::mapTicketToTicketViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketViewDto> getExpiredTickets(long userId) {

        List<Ticket> userTickets = this.ticketRepository.findAllByUserId(userId)
                .stream()
                .filter(Ticket::isFinished)
                .toList();

        return userTickets.stream()
                .map(this::mapTicketToTicketViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public Ticket getTicket(long ticketId) {
        return this.ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format(
                        ExceptionMessages.TICKET_NOT_FOUND, ticketId)
                ));
    }

    @Override
    public TicketViewDto getTicketDto(long ticketId) {
        Ticket ticket = this.getTicket(ticketId);

        return this.mapTicketToTicketViewDto(ticket);
    }

    private TicketViewDto mapTicketToTicketViewDto(Ticket ticket) {
        TicketViewDto ticketDto = this.modelMapper.map(ticket, TicketViewDto.class);

        ticketDto.setCityName(ticket.getLocation().name());

        return ticketDto;
    }
}
