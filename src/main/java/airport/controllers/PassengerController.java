package airport.controllers;

import airport.dtos.PassengerDto;
import airport.services.PassengerService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    protected Service<PassengerDto, Long> getService() {
        return passengerService;
    }

}
