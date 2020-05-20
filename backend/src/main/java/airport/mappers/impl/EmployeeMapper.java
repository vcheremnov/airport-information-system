package airport.mappers.impl;

import airport.dtos.EmployeeDto;
import airport.dtos.TeamDto;
import airport.entities.Employee;
import airport.entities.Team;
import airport.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class EmployeeMapper extends AbstractMapper<Employee, EmployeeDto, Long> {

    private final JpaRepository<Team, Long> teamRepository;

    @Autowired
    public EmployeeMapper(ModelMapper mapper,
                          JpaRepository<Team, Long> teamRepository) {
        super(mapper, Employee.class, EmployeeDto.class);
        this.teamRepository = teamRepository;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(EmployeeDto::setTeamId);
        skipDtoField(EmployeeDto::setDepartmentId);

        skipEntityField(Employee::setTeam);
        skipEntityField(Employee::setMedicalExaminations);
    }

    @Override
    protected void mapSpecificFields(Employee sourceEntity, EmployeeDto destinationDto) {
        Team team = sourceEntity.getTeam();
        destinationDto.setTeamId(team.getId());
        destinationDto.setDepartmentId(team.getDepartment().getId());
    }

    @Override
    protected void mapSpecificFields(EmployeeDto sourceDto, Employee destinationEntity) {
        destinationEntity.setTeam(teamRepository.getOne(sourceDto.getTeamId()));
    }

}

