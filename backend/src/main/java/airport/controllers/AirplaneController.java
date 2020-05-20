package airport.controllers;

import airport.dtos.*;
import airport.filters.AirplaneFilter;
import airport.filters.EmployeeFilter;
import airport.services.AirplaneService;
import airport.services.Service;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Collection;
import java.sql.Date;

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
            @PathVariable Long id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(airplaneService.getTechInspections(id, pageable));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<AirplaneDto>> search(
            @RequestBody AirplaneFilter filter,
            Pageable pageable
    ) {
        return ResponseEntity.ok(airplaneService.search(filter, pageable));
    }

//    @GetMapping("/at-the-airport")
//    public ResponseEntity<Page<AirplaneDto>> getAirplanesAtTheAirport(
//            @RequestParam("time") Date time,
//            Pageable pageable
//    ) {
//        return ResponseEntity.ok(airplaneService.getAirplanesAtTheAirport(time, pageable));
//    }

    @Override
    protected Service<AirplaneDto, Long> getService() {
        return airplaneService;
    }

}
