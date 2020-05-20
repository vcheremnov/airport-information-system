package airport.controllers;

import airport.dtos.FlightDto;
import airport.dtos.PassengerDto;
import airport.filters.FlightFilter;
import airport.filters.PassengerFilter;
import airport.services.PassengerService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passengers")
public class PassengerController extends AbstractController<PassengerDto, Long> {

    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService PassengerService) {
        this.passengerService = PassengerService;
    }

    @PostMapping("/search")
    public ResponseEntity<Page<PassengerDto>> search(
            Pageable pageable,
            @RequestBody PassengerFilter filter
    ) {
        return ResponseEntity.ok(passengerService.search(filter, pageable));
    }


    @Override
    protected Service<PassengerDto, Long> getService() {
        return passengerService;
    }

}
