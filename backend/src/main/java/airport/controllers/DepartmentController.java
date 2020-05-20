package airport.controllers;

import airport.dtos.DepartmentDto;
import airport.dtos.RepairDto;
import airport.dtos.TeamDto;
import airport.services.DepartmentService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/departments")
public class DepartmentController extends AbstractController<DepartmentDto, Long> {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService DepartmentService) {
        this.departmentService = DepartmentService;
    }

    @GetMapping("/{id}/teams")
    public ResponseEntity<Collection<TeamDto>> getRepairs(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(departmentService.getTeams(id));
    }

    @Override
    protected Service<DepartmentDto, Long> getService() {
        return departmentService;
    }

}
