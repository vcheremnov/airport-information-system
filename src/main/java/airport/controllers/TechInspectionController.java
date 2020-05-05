package airport.controllers;

import airport.dtos.TechInspectionDto;
import airport.services.TechInspectionService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tech-inspections")
public class TechInspectionController extends AbstractController<TechInspectionDto, Long> {

    private final TechInspectionService techInspectionService;

    @Autowired
    public TechInspectionController(TechInspectionService TechInspectionService) {
        this.techInspectionService = TechInspectionService;
    }

    @Override
    protected Service<TechInspectionDto, Long> getService() {
        return techInspectionService;
    }

}
