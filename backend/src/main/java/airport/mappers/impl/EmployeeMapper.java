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
    private final Mapper<Team, TeamDto, Long> teamMapper;

    @Autowired
    public EmployeeMapper(ModelMapper mapper,
                          JpaRepository<Team, Long> teamRepository,
                          Mapper<Team, TeamDto, Long> teamMapper) {
        super(mapper, Employee.class, EmployeeDto.class);
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(EmployeeDto::setTeam);

        skipEntityField(Employee::setTeam);
        skipEntityField(Employee::setMedicalExaminations);
    }

    @Override
    protected void mapSpecificFields(Employee sourceEntity, EmployeeDto destinationDto) {
        destinationDto.setTeam(teamMapper.toDto(sourceEntity.getTeam()));
    }

    @Override
    protected void mapSpecificFields(EmployeeDto sourceDto, Employee destinationEntity) {
        destinationEntity.setTeam(
                getEntityByIdOrThrow(teamRepository, sourceDto.getTeam().getId())
        );
    }

}

