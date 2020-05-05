package airport.services.impl;

import airport.dtos.DepartmentDto;
import airport.dtos.TeamDto;
import airport.entities.Department;
import airport.entities.Team;
import airport.mappers.Mapper;
import airport.repositories.DepartmentRepository;
import airport.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl
        extends AbstractService<Department, DepartmentDto, Long>
        implements DepartmentService {

    private final DepartmentRepository repository;
    private final Mapper<Department, DepartmentDto, Long> mapper;
    private final Mapper<Team, TeamDto, Long> teamMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository,
                                 Mapper<Department, DepartmentDto, Long> mapper,
                                 Mapper<Team, TeamDto, Long> teamMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.teamMapper = teamMapper;
    }

    @Override
    public Collection<TeamDto> getTeams(Long departmentId) {
        return getEntityByIdOrThrow(departmentId)
                .getTeams()
                .stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    protected JpaRepository<Department, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Department, DepartmentDto, Long> getMapper() {
        return mapper;
    }
}
