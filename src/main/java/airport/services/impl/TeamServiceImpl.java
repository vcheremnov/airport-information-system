package airport.services.impl;

import airport.dtos.EmployeeDto;
import airport.dtos.TeamDto;
import airport.entities.Employee;
import airport.entities.Team;
import airport.mappers.Mapper;
import airport.repositories.TeamRepository;
import airport.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl
        extends AbstractService<Team, TeamDto, Long>
        implements TeamService {

    private final TeamRepository repository;
    private final Mapper<Team, TeamDto, Long> mapper;
    private final Mapper<Employee, EmployeeDto, Long> employeeMapper;

    @Autowired
    public TeamServiceImpl(TeamRepository repository,
                           Mapper<Team, TeamDto, Long> mapper,
                           Mapper<Employee, EmployeeDto, Long> employeeMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Collection<EmployeeDto> getEmployees(Long teamId) {
        return getEntityByIdOrThrow(teamId)
                .getEmployees()
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    protected JpaRepository<Team, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Team, TeamDto, Long> getMapper() {
        return mapper;
    }
}
