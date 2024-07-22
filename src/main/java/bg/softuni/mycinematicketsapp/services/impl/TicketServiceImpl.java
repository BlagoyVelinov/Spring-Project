package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.BookingTimeDto;
import bg.softuni.mycinematicketsapp.models.dtos.MovieViewDto;
import bg.softuni.mycinematicketsapp.models.dtos.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.BookingTime;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.repository.OrderRepository;
import bg.softuni.mycinematicketsapp.repository.TicketRepository;
import bg.softuni.mycinematicketsapp.repository.UserRepository;
import bg.softuni.mycinematicketsapp.services.MovieService;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public void createTickets(TicketViewDto createTicket, long orderId, long movieId) {
        Order order = this.orderService.getOrderById(orderId);
        MovieViewDto movieViewDto = this.movieService.getMovieViewById(movieId);
        UserEntity user = order.getUser();

        List<Ticket> ticketsToSave = new ArrayList<>();
        for (int i = 0; i < order.getTicketsQuantity(); i++) {
            Ticket ticket = this.mapTicketToTicketViewDto(createTicket, movieViewDto, order.getBookingTime(), order);
            ticketsToSave.add(ticket);
        }
        this.ticketRepository.saveAll(ticketsToSave);

        user.setTickets(ticketsToSave);
        this.userRepository.save(user);

        order.setTickets(ticketsToSave);
        this.orderRepository.save(order);
    }

    @Override
    public List<Ticket> getTicketsByOrder(Order order) {
        return order.getTickets();
    }

    private Ticket mapTicketToTicketViewDto(TicketViewDto createTicket, MovieViewDto movieViewDto, String bookingTime, Order order) {
        return new Ticket()
                .setMovieName(movieViewDto.getName())
                .setHallNumber(createTicket.getHallNumber())
                .setPrice(createTicket.getPrice())
                .setProjectionDate(order.getProjectionDate())
                .setTicketType(createTicket.getTicketType())
                .setBookingTime(bookingTime)
                .setCity(order.getCity())
                .setMovieClassDescription(movieViewDto.getMovieClass().getDescription());
    }
}
