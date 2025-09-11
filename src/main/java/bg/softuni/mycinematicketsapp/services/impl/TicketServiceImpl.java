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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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

        tickets.forEach(ticket -> ticket.setUserId(order.getUser().getId()));

        this.ticketRepository.saveAll(tickets);
    }


    @Override
    public List<TicketViewDto> getUpcomingTickets(long userId) {

        List<Ticket> userTickets = this.ticketRepository.findAllByUserIdAndFinishedIsFalseOrderByProjectionDate(userId);

        return userTickets.stream()
                .map(this::mapTicketToTicketViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TicketViewDto> getExpiredTickets(long userId) {

        List<Ticket> userTickets = this.ticketRepository.findAllByUserIdAndFinishedIsTrueOrderByProjectionDate(userId);

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
        ticketDto.setUserId(ticket.getUserId());

        return ticketDto;
    }

    /**
     * This method start every hour by dint of Scheduling and cron!
     * Check for expired tickets in all not finished tickets and set them of finish!
     */

    @Scheduled(cron = "0 0 * * * *")
    public void updateFinishedTickets() {
        LocalTime nowTime = LocalTime.now();
        int updated = ticketRepository.markFinishedTickets(nowTime);
        System.out.println("Updated tickets: " + updated);
    }
}
