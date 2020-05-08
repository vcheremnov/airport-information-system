package airport.controllers;

import airport.dtos.FlightDto;
import airport.dtos.TicketDto;
import airport.filters.FlightFilter;
import airport.filters.TicketFilter;
import airport.services.FlightService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Override
    protected Service<FlightDto, Long> getService() {
        return flightService;
    }

}
