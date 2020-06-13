package airport.controllers;

import airport.dtos.FlightDelayDto;
import airport.dtos.FlightDto;
import airport.dtos.TicketDto;
import airport.dtos.parameters.FlightDelayInfo;
import airport.entities.types.FlightDelayReason;
import airport.filters.FlightFilter;
import airport.filters.TicketFilter;
import airport.services.FlightService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@RestController
@RequestMapping("/flights")
public class FlightController extends AbstractController<FlightDto, Long> {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService FlightService) {
        this.flightService = FlightService;
    }

    @PostMapping("/search")
    public ResponseEntity<Page<FlightDto>> search(
            Pageable pageable,
            @RequestBody FlightFilter filter
    ) {
        return ResponseEntity.ok(flightService.search(filter, pageable));
    }

    @GetMapping("/{flightId}/tickets")
    public ResponseEntity<Page<TicketDto>> getTickets(
            @PathVariable("flightId") Long flightId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(flightService.getTickets(flightId, pageable));
    }

    @PostMapping("/{flightId}/tickets")
    public ResponseEntity<TicketDto> addTicket(
            @PathVariable("flightId") Long flightId,
            @RequestBody TicketDto ticketDto
    ) {
        return ResponseEntity.ok(flightService.addTicket(flightId, ticketDto));
    }


    @PutMapping("/{flightId}/delay")
    public ResponseEntity<FlightDto> delayFlight(
            @PathVariable("flightId") Long flightId,
            @RequestBody FlightDelayInfo flightDelayInfo
    ) {
        return ResponseEntity.ok(flightService.delayFlight(flightId, flightDelayInfo));
    }

    @Override
    protected Service<FlightDto, Long> getService() {
        return flightService;
    }

}
