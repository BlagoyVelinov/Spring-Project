package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.view.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.UpdateTicketDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.TicketType;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.repository.TicketRepository;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import jakarta.transaction.Transactional;
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
    private final UserRepository userRepository;
    private final OrderService orderService;
    private final MovieService movieService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, OrderRepository orderRepository,
                             UserRepository userRepository, OrderService orderService, MovieService movieService) {
        this.ticketRepository = ticketRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.movieService = movieService;
    }

    @Override
    @Transactional
    public void createTickets(TicketViewDto createTicket, long orderId, long movieId) {
        Order order = this.orderService.getOrderById(orderId);

        MovieViewDto movieViewDto = this.movieService.getMovieViewById(movieId);
        UserEntity user = order.getUser();
        List<Ticket> unfinishedTicketsByUser = this.getNotFinishedTicketsByUser(user);

        if (!unfinishedTicketsByUser.isEmpty()) {
            this.removeAllNotFinishedTickets(unfinishedTicketsByUser, order, user);
        }

        List<Ticket> ticketsToSave = getTicketList(createTicket, movieViewDto, order);
        order.setTickets(ticketsToSave);

        this.ticketRepository.saveAll(ticketsToSave);
        this.orderRepository.save(order);

        user.setTickets(ticketsToSave);
        this.userRepository.save(user);

    }


    @Override
    public List<Ticket> getTicketsByOrder(Order order) {
        return order.getTickets();
    }


    @Override
    public void updateTickets(UpdateTicketDto updateTicket, long orderId, long movieId) {
        Order order = this.orderService.getOrderById(orderId);
        String[][] cinemaHall = updateTicket.getSeats();


        AtomicInteger lastRow = new AtomicInteger();
        order.getTickets().forEach(ticket -> {

            this.addSelectedSeadToTicket(ticket, lastRow, cinemaHall);

            this.ticketRepository.save(ticket);
        });

    }

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
        return new TicketViewDto()
                .setTicketType(ticket.getTicketType())
                .setId(ticket.getId())
                .setCity(ticket.getCity().getLocation().getValue())
                .setPrice(ticket.getPrice())
                .setHallNumber(ticket.getHallNumber())
                .setBookingTime(ticket.getBookingTime())
                .setMovieClass(ticket.getMovieClassDescription())
                .setMovieName(ticket.getMovieName())
                .setProjectionDate(ticket.getProjectionDate())
                .setNumberOfRow(ticket.getNumberOfRow())
                .setNumberOfSeat(ticket.getNumberOfSeat());
    }

    private void addSelectedSeadToTicket(Ticket ticket, AtomicInteger lastRow, String[][] cinemaHall) {
        for (int i = lastRow.get(); i < cinemaHall.length; i++) {
            boolean isBooked = false;
            for (int j = 0; j < cinemaHall[i].length; j++) {
                String selectedSeat = cinemaHall[i][j];
                if (selectedSeat != null) {
                    isBooked = this.isBooked(ticket, i, j, cinemaHall, lastRow);
                    break;
                }
            }
            if (isBooked) {
                break;
            }

        }
    }

    private boolean isBooked(Ticket ticket, int i, int j, String[][] cinemaHall, AtomicInteger lastRow) {
        ticket.setNumberOfRow(i + 1);
        ticket.setNumberOfSeat(j + 1);
        cinemaHall[i][j] = null;
        lastRow.set(i);
        return true;
    }

    private List<Ticket> getTicketList(TicketViewDto createTicket, MovieViewDto movieViewDto, Order order) {
        List<Ticket> ticketsToSave = new ArrayList<>();

        for (int i = 0; i < order.getChildQuantity(); i++) {
            TicketType ticketType = TicketType.CHILDREN_UNDER_16;
            Ticket ticket = this.mapTicketViewDtoToTicket(createTicket, movieViewDto, ticketType, order.getBookingTime(), order);
            ticketsToSave.add(ticket);
        }
        for (int i = 0; i < order.getRegularQuantity(); i++) {
            TicketType ticketType = TicketType.REGULAR;
            Ticket ticket = this.mapTicketViewDtoToTicket(createTicket, movieViewDto, ticketType, order.getBookingTime(), order);
            ticketsToSave.add(ticket);
        }
        for (int i = 0; i < order.getStudentQuantity(); i++) {
            TicketType ticketType = TicketType.PUPILS_AND_STUDENTS;
            Ticket ticket = this.mapTicketViewDtoToTicket(createTicket, movieViewDto, ticketType, order.getBookingTime(), order);
            ticketsToSave.add(ticket);
        }
        for (int i = 0; i < order.getOverSixtyQuantity(); i++) {
            TicketType ticketType = TicketType.PERSONS_OVER_60;
            Ticket ticket = this.mapTicketViewDtoToTicket(createTicket, movieViewDto, ticketType, order.getBookingTime(), order);
            ticketsToSave.add(ticket);
        }
        return ticketsToSave;
    }


    private Ticket mapTicketViewDtoToTicket(TicketViewDto createTicket, MovieViewDto movieViewDto,
                                            TicketType ticketType, String bookingTime, Order order) {
        return new Ticket()
                .setMovieName(movieViewDto.getName())
                .setHallNumber(movieViewDto.getHallNumber())
                .setPrice(ticketType.getPrice())
                .setProjectionDate(order.getProjectionDate())
                .setNumberOfRow(createTicket.getNumberOfRow())
                .setNumberOfSeat(createTicket.getNumberOfSeat())
                .setTicketType(ticketType)
                .setBookingTime(bookingTime)
                .setCity(order.getCity())
                .setMovieClassDescription(movieViewDto.getMovieClass().getDescription());
    }

    private void removeAllNotFinishedTickets(List<Ticket> unfinishedTicketsByUser, Order order, UserEntity user) {

        order.getTickets().removeAll(unfinishedTicketsByUser);
        user.getTickets().removeAll(unfinishedTicketsByUser);

        this.orderRepository.save(order);
        this.userRepository.save(user);
        unfinishedTicketsByUser
                .forEach(ticket -> this.ticketRepository.deleteById(ticket.getId()));

    }

    private List<Ticket> getNotFinishedTicketsByUser(UserEntity user) {
        return user.getTickets()
                .stream()
                .filter(ticket -> !ticket.isFinished())
                .toList();
    }
}
