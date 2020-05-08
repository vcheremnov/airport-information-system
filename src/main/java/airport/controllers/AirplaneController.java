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
    public ResponseEntity<Page<RepairDto>> getRepairs(
            @PathVariable Long id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(airplaneService.getRepairs(id, pageable));
    }

    @GetMapping("/{id}/tech-inspections")
    public ResponseEntity<Page<TechInspectionDto>> getTechInspections(
            Pageable pageable,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(airplaneService.getTechInspections(id, pageable));
    }

    @GetMapping("/inspected-between")
    public ResponseEntity<Page<AirplaneDto>> getInspectedBetween(
            @RequestParam Date minDate,
            @RequestParam Date maxDate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(airplaneService.getInspectedBetween(minDate, maxDate, pageable));
    }

    @GetMapping("/repaired-between")
    public ResponseEntity<Page<AirplaneDto>> getRepairedBetween(
            @RequestParam Date minDate,
            @RequestParam Date maxDate,
            Pageable pageable
    ) {
        return ResponseEntity.ok(airplaneService.getRepairedBetween(minDate, maxDate, pageable));
    }

    @Override
    protected Service<AirplaneDto, Long> getService() {
        return airplaneService;
    }

}
