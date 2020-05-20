package airport.services;

import airport.dtos.EmployeeDto;
import airport.dtos.TeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface TeamService extends Service<TeamDto, Long> {
    Page<EmployeeDto> getEmployees(Long teamId, Pageable pageable);
}
