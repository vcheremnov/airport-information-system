package airport.services;

import airport.dtos.DepartmentDto;
import airport.dtos.TeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface DepartmentService extends Service<DepartmentDto, Long> {
    Page<TeamDto> getTeams(Long departmentId, Pageable pageable);
}
