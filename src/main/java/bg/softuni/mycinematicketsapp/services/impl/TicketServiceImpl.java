package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.repository.TicketRepository;
import bg.softuni.mycinematicketsapp.services.TicketService;
import bg.softuni.mycinematicketsapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, UserService userService, ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
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

    @Override
    public List<TicketViewDto> getUpcomingTickets(long userId) {
        UserEntity user = this.userService.getUserById(userId);

        List<Ticket> userTickets = user.getTickets()
                .stream()
                .filter(ticket -> !ticket.isFinished())
                .toList();

        return userTickets.stream()
                .map(this::mapTicketToTicketViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketViewDto> getExpiredTickets(long userId) {
        UserEntity user = this.userService.getUserById(userId);

        List<Ticket> userTickets = user.getTickets()
                .stream()
                .filter(Ticket::isFinished)
                .toList();

        return userTickets.stream()
                .map(this::mapTicketToTicketViewDto)
                .collect(Collectors.toList());
    }

    private TicketViewDto mapTicketToTicketViewDto(Ticket ticket) {
        return this.modelMapper.map(ticket, TicketViewDto.class);
    }
}
