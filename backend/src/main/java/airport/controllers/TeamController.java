package airport.controllers;

import airport.dtos.EmployeeDto;
import airport.dtos.TeamDto;
import airport.services.TeamService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/teams")
public class TeamController extends AbstractController<TeamDto, Long> {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService TeamService) {
        this.teamService = TeamService;
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<Collection<EmployeeDto>> getEmployees(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(teamService.getEmployees(id));
    }

    @Override
    protected Service<TeamDto, Long> getService() {
        return teamService;
    }

}
