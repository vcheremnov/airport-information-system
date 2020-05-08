package airport.controllers;

import airport.dtos.TicketDto;
import airport.filters.TicketFilter;
import airport.services.TicketService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController extends AbstractController<TicketDto, Long> {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService TicketService) {
        this.ticketService = TicketService;
    }

    @PostMapping("/search")
    public ResponseEntity<Page<TicketDto>> getTickets(
            @RequestBody TicketFilter filter,
            Pageable pageable
    ) {
        return ResponseEntity.ok(ticketService.getTickets(filter, pageable));
    }

    @Override
    protected Service<TicketDto, Long> getService() {
        return ticketService;
    }

}
