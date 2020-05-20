package airport.mappers.impl;

import airport.dtos.MedicalExaminationDto;
import airport.entities.Employee;
import airport.entities.MedicalExamination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MedicalExaminationMapper
        extends AbstractMapper<MedicalExamination, MedicalExaminationDto, Long> {

    private final JpaRepository<Employee, Long> employeeRepository;

    @Autowired
    public MedicalExaminationMapper(ModelMapper mapper,
                                    JpaRepository<Employee, Long> employeeRepository) {
        super(mapper, MedicalExamination.class, MedicalExaminationDto.class);
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(MedicalExaminationDto::setEmployeeId);

        skipEntityField(MedicalExamination::setEmployee);
    }

    @Override
    protected void mapSpecificFields(MedicalExamination sourceEntity,
                                     MedicalExaminationDto destinationDto) {
        destinationDto.setEmployeeId(sourceEntity.getEmployee().getId());
    }

    @Override
    protected void mapSpecificFields(MedicalExaminationDto sourceDto,
                                     MedicalExamination destinationEntity) {
        destinationEntity.setEmployee(employeeRepository.getOne(sourceDto.getEmployeeId()));
    }
}
