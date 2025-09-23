package bg.softuni.mycinematicketsapp.services.impl;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.models.entities.Order;
import bg.softuni.mycinematicketsapp.models.entities.Ticket;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.repository.TicketRepository;
import bg.softuni.mycinematicketsapp.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void testGetUpcomingTickets() {
        Ticket ticket1 = getticket(1L, 2L);
        Ticket ticket2 = getticket(2L, 2L);
        Ticket ticket3 = getticket(3L, 2L);
        ticket3.setFinished(true);

        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);

        Mockito.when(ticketRepository.findAllByUserIdAndFinishedIsFalseOrderByProjectionDate(Mockito.anyLong()))
                .thenAnswer(invocation -> {
                    Long userId = invocation.getArgument(0, Long.class);
                    return tickets.stream()
                            .filter(t -> t.getUserId() == userId && !t.isFinished())
                            .toList();
                });
        Mockito.when(modelMapper.map(ticket1, TicketViewDto.class)).thenReturn(new TicketViewDto());
        Mockito.when(modelMapper.map(ticket2, TicketViewDto.class)).thenReturn(new TicketViewDto());

        List<TicketViewDto> upcomingTickets = ticketService.getUpcomingTickets(2L);

        Assertions.assertNotNull(upcomingTickets);
        assertEquals(2, upcomingTickets.size());

        Mockito.verify(ticketRepository, Mockito.times(1)).findAllByUserIdAndFinishedIsFalseOrderByProjectionDate(2L);
    }

    @Test
    void testGetExpiredTickets() {
        Ticket ticket1 = getticket(1L, 2L);
        Ticket ticket2 = getticket(2L, 2L);
        Ticket ticket3 = getticket(3L, 2L);
        ticket2.setFinished(true);
        ticket3.setFinished(true);

        List<Ticket> tickets = List.of(ticket1, ticket2, ticket3);

        Mockito.when(ticketRepository.findAllByUserIdAndFinishedIsTrueOrderByProjectionDate(Mockito.anyLong()))
                .thenAnswer(invocation -> {
                    Long userId = invocation.getArgument(0, Long.class);
                    return tickets.stream()
                            .filter(t -> t.getUserId() == userId && t.isFinished())
                            .toList();
                });
        Mockito.when(modelMapper.map(ticket2, TicketViewDto.class)).thenReturn(new TicketViewDto());
        Mockito.when(modelMapper.map(ticket3, TicketViewDto.class)).thenReturn(new TicketViewDto());

        List<TicketViewDto> expiredTickets = ticketService.getExpiredTickets(2L);

        Assertions.assertNotNull(expiredTickets);
        assertEquals(2, expiredTickets.size());

        Mockito.verify(ticketRepository, Mockito.times(1)).findAllByUserIdAndFinishedIsTrueOrderByProjectionDate(2L);
    }

    @Test
    void testGetTicketById_Success() {
        Ticket ticket = getticket(1L, 1L);

        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.getTicket(ticket.getId());

        Assertions.assertNotNull(result);
        assertEquals(result.getId(), ticket.getId());
        assertEquals(result.getUserId(), ticket.getUserId());
        assertEquals(result.getLocation(), ticket.getLocation());
    }

    @Test
    void testGetTicketById_NotFound() {

        when(ticketRepository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> ticketService.getTicket(10L));
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
        ticket.setFinished(false);
        ticket.setLocation(CityName.SOFIA);

        return ticket;
    }
}
