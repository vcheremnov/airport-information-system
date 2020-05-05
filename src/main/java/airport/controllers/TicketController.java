package airport.controllers;

import airport.dtos.TicketDto;
import airport.services.TicketService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
public class TicketController extends AbstractController<TicketDto, Long> {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService TicketService) {
        this.ticketService = TicketService;
    }

    @Override
    protected Service<TicketDto, Long> getService() {
        return ticketService;
    }

}
