package airport.mappers.impl;

import airport.dtos.EmployeeDto;
import airport.entities.AbstractEntity;
import airport.entities.Employee;
import airport.entities.MedicalExamination;
import airport.entities.Team;
import airport.repositories.TeamRepository;
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

        skipEntityField(Employee::setTeam);
        skipEntityField(Employee::setMedicalExaminations);
    }

    @Override
    protected void mapSpecificFields(Employee sourceEntity, EmployeeDto destinationDto) {
        destinationDto.setTeamId(sourceEntity.getTeam().getId());
        destinationDto.setDepartmentId(sourceEntity.getTeam().getDepartment().getId());
    }

    @Override
    protected void mapSpecificFields(EmployeeDto sourceDto, Employee destinationEntity) {
        destinationEntity.setTeam(teamRepository.getOne(sourceDto.getTeamId()));
    }

}

