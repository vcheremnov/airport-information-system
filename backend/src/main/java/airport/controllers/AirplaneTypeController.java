package airport.controllers;

import airport.dtos.AirplaneTypeDto;
import airport.services.AirplaneTypeService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/airplane-types")
public class AirplaneTypeController extends AbstractController<AirplaneTypeDto, Long> {

    private final AirplaneTypeService airplaneTypeService;

    @Autowired
    public AirplaneTypeController(AirplaneTypeService AirplaneTypeService) {
        this.airplaneTypeService = AirplaneTypeService;
    }

    @Override
    protected Service<AirplaneTypeDto, Long> getService() {
        return airplaneTypeService;
    }

}
