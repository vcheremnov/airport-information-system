package airport.services.impl;

import airport.dtos.DepartmentDto;
import airport.dtos.TeamDto;
import airport.entities.Department;
import airport.entities.Team;
import airport.mappers.Mapper;
import airport.repositories.DepartmentRepository;
import airport.repositories.TeamRepository;
import airport.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl
        extends AbstractService<Department, DepartmentDto, Long>
        implements DepartmentService {

    private final DepartmentRepository repository;
    private final TeamRepository teamRepository;
    private final Mapper<Department, DepartmentDto, Long> mapper;
    private final Mapper<Team, TeamDto, Long> teamMapper;

    @Autowired
    public DepartmentServiceImpl(
            DepartmentRepository repository,
            TeamRepository teamRepository,
            Mapper<Department, DepartmentDto, Long> mapper,
            Mapper<Team, TeamDto, Long> teamMapper
    ) {
        this.repository = repository;
        this.teamRepository = teamRepository;
        this.mapper = mapper;
        this.teamMapper = teamMapper;
    }

    @Override
    public Page<TeamDto> getTeams(Long departmentId, Pageable pageable) {
        return teamRepository
                .getAllByDepartmentId(departmentId, pageable)
                .map(teamMapper::toDto);
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
