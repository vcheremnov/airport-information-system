package airport.services;

import airport.dtos.DepartmentDto;
import airport.dtos.TeamDto;

import java.util.Collection;

public interface DepartmentService extends Service<DepartmentDto, Long> {
    Collection<TeamDto> getTeams(Long departmentId);
}
