package airport.services;

import airport.dtos.EmployeeDto;
import airport.dtos.TeamDto;

import java.util.Collection;

public interface TeamService extends Service<TeamDto, Long> {
    Collection<EmployeeDto> getEmployees(Long teamId);
}
