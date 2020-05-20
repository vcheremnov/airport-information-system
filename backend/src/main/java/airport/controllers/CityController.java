package airport.controllers;

import airport.dtos.CityDto;
import airport.services.CityService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CityController extends AbstractController<CityDto, Long> {

    private final CityService cityService;

    @Autowired
    public CityController(CityService CityService) {
        this.cityService = CityService;
    }

    @Override
    protected Service<CityDto, Long> getService() {
        return cityService;
    }

}
