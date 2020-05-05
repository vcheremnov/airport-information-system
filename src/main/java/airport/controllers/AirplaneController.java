package airport.controllers;

import airport.dtos.AirplaneDto;
import airport.dtos.EmployeeDto;
import airport.dtos.RepairDto;
import airport.dtos.TechInspectionDto;
import airport.services.AirplaneService;
import airport.services.Service;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Collection;

@RestController
@RequestMapping("/airplanes")
public class AirplaneController extends AbstractController<AirplaneDto, Long> {

    private final AirplaneService airplaneService;

    @Autowired
    public AirplaneController(AirplaneService AirplaneService) {
        this.airplaneService = AirplaneService;
    }

    @GetMapping("/{id}/repairs")
    public ResponseEntity<Collection<RepairDto>> getRepairs(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(airplaneService.getRepairs(id));
    }

    @GetMapping("/{id}/tech-inspections")
    public ResponseEntity<Page<TechInspectionDto>> getTechInspections(
            Pageable pageable,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(airplaneService.getTechInspections(pageable, id));
    }

    @GetMapping("/inspected-between")
    public ResponseEntity<Collection<AirplaneDto>> getInspectedBetween(
            @RequestParam Date minDate,
            @RequestParam Date maxDate
    ) {
        return ResponseEntity.ok(airplaneService.getInspectedBetween(minDate, maxDate));
    }

    @GetMapping("/repaired-between")
    public ResponseEntity<Collection<AirplaneDto>> getRepairedBetween(
            @RequestParam Date minDate,
            @RequestParam Date maxDate
    ) {
        return ResponseEntity.ok(airplaneService.getRepairedBetween(minDate, maxDate));
    }

    @Override
    protected Service<AirplaneDto, Long> getService() {
        return airplaneService;
    }

}
