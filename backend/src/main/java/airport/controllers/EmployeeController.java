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
            @RequestBody EmployeeFilter filter,
            Pageable pageable
    ) {
        return ResponseEntity.ok(employeeService.search(filter, pageable));
    }

    @GetMapping("/{id}/med-exams")
    public ResponseEntity<Page<MedicalExaminationDto>> getMedExams(
            @PathVariable Long id,
            Pageable pageable
    ) {
        return ResponseEntity.ok(employeeService.getMedicalExaminations(id, pageable));
    }

    @Override
    protected Service<EmployeeDto, Long> getService() {
        return employeeService;
    }

}
