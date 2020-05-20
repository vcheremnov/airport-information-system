package airport.controllers;

import airport.dtos.FlightDelayDto;
import airport.services.FlightDelayService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flight-delays")
public class FlightDelayController extends AbstractController<FlightDelayDto, Long> {

    private final FlightDelayService flightDelayService;

    @Autowired
    public FlightDelayController(FlightDelayService FlightDelayService) {
        this.flightDelayService = FlightDelayService;
    }

    @Override
    protected Service<FlightDelayDto, Long> getService() {
        return flightDelayService;
    }

}
