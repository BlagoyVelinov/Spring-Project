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

}
