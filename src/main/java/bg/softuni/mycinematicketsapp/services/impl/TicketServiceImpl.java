package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.repository.TicketRepository;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, OrderRepository orderRepository,
                             OrderService orderService,
                             ModelMapper modelMapper) {
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    /**
     * Adding all ticket in map to get all quantity tickets by type
     */
    @Override
    public Map<String, List<TicketViewDto>> addToTicketsMap(long orderId) {
        Order order = this.orderService.getOrderById(orderId);
        Map<String, List<TicketViewDto>> ticketsMap = new HashMap<>();
        order.getTickets().forEach(ticket -> {
            ticketsMap.putIfAbsent(ticket.getTicketType().getValue(), new ArrayList<>());
            ticketsMap.get(ticket.getTicketType().getValue()).add(this.mapTicketToTicketViewDto(ticket));
        });
        return ticketsMap;
    }

    /**
     * I collect the seats on all the tickets and arrange them in a list by row
     */
    @Override
    public Map<Integer, List<Integer>> getSeatsByRow(long orderId) {
        Order order = this.orderService.getOrderById(orderId);
        Map<Integer, List<Integer>> rowSeats = new HashMap<>();
        order.getTickets().forEach(ticket -> {
            rowSeats.putIfAbsent(ticket.getNumberOfRow(), new ArrayList<>());
            rowSeats.get(ticket.getNumberOfRow()).add(ticket.getNumberOfSeat());
        });
        return rowSeats;
    }

    @Override
    public void confirmOrder(long orderId) {
        Order order = this.orderService.getOrderById(orderId);
        List<Ticket> tickets = new ArrayList<>();
        order.getTickets().forEach(ticket -> {
            ticket.setFinished(true);
            tickets.add(ticket);
        });
        this.ticketRepository.saveAll(tickets);

        order.setFinished(true);
        this.orderRepository.save(order);
    }

    private TicketViewDto mapTicketToTicketViewDto(Ticket ticket) {
        return this.modelMapper.map(ticket, TicketViewDto.class);
    }

    private boolean isBooked(Ticket ticket, int i, int j, String[][] cinemaHall, AtomicInteger lastRow) {
        ticket.setNumberOfRow(i + 1);
        ticket.setNumberOfSeat(j + 1);
        cinemaHall[i][j] = null;
        lastRow.set(i);
        return true;
    }
}
