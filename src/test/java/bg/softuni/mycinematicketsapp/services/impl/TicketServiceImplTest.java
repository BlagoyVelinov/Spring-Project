package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.repository.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Captor
    private ArgumentCaptor<List<Ticket>> ticketsCaptor;

    @Test
    void testSaveTicketsForOrder() {
        Order order = getOrder();

        ticketService.saveTicketsForOrder(order);

        verify(ticketRepository).saveAll(ticketsCaptor.capture());
        List<Ticket> savedTickets = ticketsCaptor.getValue();

        assertEquals(2, savedTickets.size());
        assertEquals(42L, savedTickets.get(0).getUserId());
        assertEquals(42L, savedTickets.get(1).getUserId());
    }

    @Test
    void testGetAllTicketsByUser() {
        Ticket ticket1 = getticket(1L, 2L);
        Ticket ticket2 = getticket(2L, 2L);
        Ticket ticket3 = getticket(3L, 3L);

        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);

        Mockito.when(ticketRepository.findAllByUserId(Mockito.anyLong()))
                .thenAnswer(invocation -> {
                    Long userId = invocation.getArgument(0, Long.class);
                    return tickets.stream()
                            .filter(t -> t.getUserId() == userId)
                            .toList();
                });

        List<Ticket> ticketsByUser = ticketService.getAllTicketsByUser(2L);

        Assertions.assertNotNull(ticketsByUser);
        assertEquals(2, ticketsByUser.size());
        Assertions.assertTrue(ticketsByUser.stream().allMatch(t -> t.getUserId() == 2L));

        List<Ticket> ticketsByUser1 = ticketService.getAllTicketsByUser(3L);
        Assertions.assertEquals(1, ticketsByUser1.size());
        Assertions.assertEquals(3L, ticketsByUser1.get(0).getUserId());

        List<Ticket> ticketsByUser99 = ticketService.getAllTicketsByUser(99L);
        Assertions.assertTrue(ticketsByUser99.isEmpty());
    }

    private Order getOrder() {
        UserEntity user = new UserEntity();
        user.setId(42L);

        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();

        Order order = new Order();
        order.setUser(user);
        order.setTickets(List.of(ticket1, ticket2));

        return order;
    }

    private Ticket getticket(long id, long userId) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setUserId(userId);

        return ticket;
    }
}
