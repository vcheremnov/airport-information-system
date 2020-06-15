package airport.mappers.impl;

import airport.dtos.DepartmentDto;
import airport.dtos.TeamDto;
import airport.entities.Department;
import airport.entities.Employee;
import airport.entities.Team;
import airport.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class TeamMapper extends AbstractMapper<Team, TeamDto, Long> {

    private final JpaRepository<Department, Long> departmentRepository;
    private final Mapper<Department, DepartmentDto, Long> departmentMapper;

    @Autowired
    public TeamMapper(ModelMapper mapper,
                      JpaRepository<Department, Long> departmentRepository,
                      Mapper<Department, DepartmentDto, Long> departmentMapper) {
        super(mapper, Team.class, TeamDto.class);
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(TeamDto::setDepartment);

        skipEntityField(Team::setDepartment);
        skipEntityField(Team::setAverageSalary);
        skipEntityField(Team::setEmployees);
    }

    @Override
    protected void mapSpecificFields(Team sourceEntity, TeamDto destinationDto) {
        destinationDto.setDepartment(departmentMapper.toDto(sourceEntity.getDepartment()));
    }

    @Override
    protected void mapSpecificFields(TeamDto sourceDto, Team destinationEntity) {
        destinationEntity.setDepartment(
                getEntityByIdOrThrow(departmentRepository, sourceDto.getDepartment().getId())
        );
    }
}
