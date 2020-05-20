package airport.services.impl;

import airport.dtos.EmployeeDto;
import airport.dtos.TeamDto;
import airport.entities.Employee;
import airport.entities.Team;
import airport.mappers.Mapper;
import airport.repositories.EmployeeRepository;
import airport.repositories.TeamRepository;
import airport.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TeamServiceImpl
        extends AbstractService<Team, TeamDto, Long>
        implements TeamService {

    private final TeamRepository repository;
    private final Mapper<Team, TeamDto, Long> mapper;
    private final EmployeeRepository employeeRepository;
    private final Mapper<Employee, EmployeeDto, Long> employeeMapper;

    @Autowired
    public TeamServiceImpl(
            TeamRepository repository,
            Mapper<Team, TeamDto, Long> mapper,
            EmployeeRepository employeeRepository,
            Mapper<Employee, EmployeeDto, Long> employeeMapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Page<EmployeeDto> getEmployees(Long teamId, Pageable pageable) {
        return employeeRepository
                .getAllByTeamId(teamId, pageable)
                .map(employeeMapper::toDto);
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
