package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.services.SecurityService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final SecurityService securityService;

    public TicketController(TicketService ticketService, SecurityService securityService) {
        this.ticketService = ticketService;
        this.securityService = securityService;
    }

    @GetMapping("/upcoming-tickets/user/{id}")
    public ResponseEntity<List<TicketViewDto>> getUpcomingTicketsByUser(@PathVariable long id,
                                                                        Authentication authentication) {

        securityService.validateCurrentUser(id, authentication);

        List<TicketViewDto> upcomingTickets = ticketService.getUpcomingTickets(id);

        return ResponseEntity.ok(upcomingTickets);
    }

    @GetMapping("/expired-tickets/user/{id}")
    public ResponseEntity<List<TicketViewDto>> getExpiredTicketsByUser(@PathVariable long id,
                                                                       Authentication authentication) {

        securityService.validateCurrentUser(id, authentication);

        List<TicketViewDto> expiredTickets = ticketService.getExpiredTickets(id);

        return ResponseEntity.ok(expiredTickets);
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity<TicketViewDto> getTicketById(@PathVariable long id,
                                                       Authentication authentication) {

        securityService.validateUserForTicket(id, authentication);

        TicketViewDto ticketDto = ticketService.getTicketDto(id);

        return ResponseEntity.ok(ticketDto);
    }

    @PutMapping("/ticket-update/{id}")
    public ResponseEntity<?> updateTicketById(@PathVariable long id,
                                                       Authentication authentication) {

        securityService.validateUserForTicket(id, authentication);

        ticketService.updateFinishedTickets(id);

        return ResponseEntity.ok(Map.of("message", "Ticket updates successfully"));
    }
}
