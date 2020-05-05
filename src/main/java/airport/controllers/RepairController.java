package airport.controllers;

import airport.dtos.RepairDto;
import airport.services.RepairService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/repairs")
public class RepairController extends AbstractController<RepairDto, Long> {

    private final RepairService repairService;

    @Autowired
    public RepairController(RepairService RepairService) {
        this.repairService = RepairService;
    }

    @Override
    protected Service<RepairDto, Long> getService() {
        return repairService;
    }

}
