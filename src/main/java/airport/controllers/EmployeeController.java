package airport.controllers;

import airport.dtos.EmployeeDto;
import airport.dtos.MedicalExaminationDto;
import airport.filters.EmployeeFilter;
import airport.services.EmployeeService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Date;
import java.util.Collection;

@RestController
@RequestMapping("/employees")
public class EmployeeController extends AbstractController<EmployeeDto, Long> {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/search")
    public ResponseEntity<Page<EmployeeDto>> search(
            Pageable pageable,
            @RequestBody EmployeeFilter filter
    ) {
        return ResponseEntity.ok(employeeService.search(filter, pageable));
    }

    @GetMapping("/by-med-exam-result")
    public ResponseEntity<Page<EmployeeDto>> getByMedExamResult(
            Pageable pageable,
            @RequestParam Integer year,
            @RequestParam Boolean isPassed
    ) {
        return ResponseEntity.ok(
                employeeService.getByMedExamResult(year, isPassed, pageable)
        );
    }

    @GetMapping("/{id}/med-exams")
    public ResponseEntity<Collection<MedicalExaminationDto>> getMedExams(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(employeeService.getMedExams(id));
    }

    @Override
    protected Service<EmployeeDto, Long> getService() {
        return employeeService;
    }

}
