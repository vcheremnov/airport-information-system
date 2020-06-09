package airport.mappers.impl;

import airport.dtos.EmployeeDto;
import airport.dtos.MedicalExaminationDto;
import airport.entities.Employee;
import airport.entities.MedicalExamination;
import airport.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MedicalExaminationMapper
        extends AbstractMapper<MedicalExamination, MedicalExaminationDto, Long> {

    private final JpaRepository<Employee, Long> employeeRepository;
    private final Mapper<Employee, EmployeeDto, Long> employeeMapper;

    @Autowired
    public MedicalExaminationMapper(ModelMapper mapper,
                                    JpaRepository<Employee, Long> employeeRepository,
                                    Mapper<Employee, EmployeeDto, Long> employeeMapper) {
        super(mapper, MedicalExamination.class, MedicalExaminationDto.class);
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(MedicalExaminationDto::setEmployee);

        skipEntityField(MedicalExamination::setEmployee);
    }

    @Override
    protected void mapSpecificFields(MedicalExamination sourceEntity,
                                     MedicalExaminationDto destinationDto) {
        destinationDto.setEmployee(employeeMapper.toDto(sourceEntity.getEmployee()));
    }

    @Override
    protected void mapSpecificFields(MedicalExaminationDto sourceDto,
                                     MedicalExamination destinationEntity) {
        destinationEntity.setEmployee(
                employeeRepository.getOne(sourceDto.getEmployee().getId())
        );
    }
}
