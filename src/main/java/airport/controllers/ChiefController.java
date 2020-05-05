package airport.controllers;

import airport.dtos.ChiefDto;
import airport.services.ChiefService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chiefs")
public class ChiefController extends AbstractController<ChiefDto, Long> {

    private final ChiefService chiefService;

    @Autowired
    public ChiefController(ChiefService ChiefService) {
        this.chiefService = ChiefService;
    }

    @Override
    protected Service<ChiefDto, Long> getService() {
        return chiefService;
    }

}
