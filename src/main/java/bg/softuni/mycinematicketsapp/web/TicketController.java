package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.models.dtos.view.TicketViewDto;
import bg.softuni.mycinematicketsapp.services.SecurityService;
import bg.softuni.mycinematicketsapp.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
