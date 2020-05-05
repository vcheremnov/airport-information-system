package airport.mappers.impl;

import airport.dtos.TeamDto;
import airport.entities.AbstractEntity;
import airport.entities.Department;
import airport.entities.Employee;
import airport.entities.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class TeamMapper extends AbstractMapper<Team, TeamDto, Long> {

    private final JpaRepository<Department, Long> departmentRepository;

    @Autowired
    public TeamMapper(ModelMapper mapper,
                      JpaRepository<Department, Long> departmentRepository) {
        super(mapper, Team.class, TeamDto.class);
        this.departmentRepository = departmentRepository;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(TeamDto::setDepartmentId);

        skipEntityField(Team::setDepartment);
        skipEntityField(Team::setEmployees);
    }

    @Override
    protected void mapSpecificFields(Team sourceEntity, TeamDto destinationDto) {
        destinationDto.setDepartmentId(sourceEntity.getDepartment().getId());
    }

    @Override
    protected void mapSpecificFields(TeamDto sourceDto, Team destinationEntity) {
        destinationEntity.setDepartment(departmentRepository.getOne(sourceDto.getDepartmentId()));
    }
}
