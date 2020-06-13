package airport.services.impl;

import airport.dtos.*;
import airport.entities.*;
import airport.filters.EmployeeFilter;
import airport.mappers.Mapper;
import airport.repositories.EmployeeRepository;
import airport.repositories.MedicalExaminationRepository;
import airport.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class EmployeeServiceImpl
        extends AbstractService<Employee, EmployeeDto, Long>
        implements EmployeeService {

    private final EmployeeRepository repository;
    private final Mapper<Employee, EmployeeDto, Long> mapper;
    private final MedicalExaminationRepository medicalExaminationRepository;
    private final Mapper<MedicalExamination, MedicalExaminationDto, Long> medExamMapper;

    @Autowired
    public EmployeeServiceImpl(
            EmployeeRepository repository,
            Mapper<Employee, EmployeeDto, Long> mapper,
            MedicalExaminationRepository medicalExaminationRepository,
            Mapper<MedicalExamination, MedicalExaminationDto, Long> medExamMapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.medicalExaminationRepository = medicalExaminationRepository;
        this.medExamMapper = medExamMapper;
    }

    @Override
    public EmployeeDto create(EmployeeDto employeeDto) {
        employeeDto.setEmploymentDate(new Date(System.currentTimeMillis()));
        return super.create(employeeDto);
    }

    @Override
    public Page<EmployeeDto> search(EmployeeFilter filter, Pageable pageable) {
        filter.setName(prepareStringToLikeStatement(filter.getName()));
        filter.setTeamName(prepareStringToLikeStatement(filter.getTeamName()));
        filter.setDepartmentName(prepareStringToLikeStatement(filter.getDepartmentName()));
        return repository.searchByFilter(
                filter.getSex(),
                filter.getName(),
                filter.getTeamName(),
                filter.getDepartmentName(),
                filter.getMinBirthDate(),
                filter.getMaxBirthDate(),
                filter.getMinEmploymentDate(),
                filter.getMaxEmploymentDate(),
                filter.getMinSalary(),
                filter.getMaxSalary(),
                filter.getMinTeamAverageSalary(),
                filter.getMaxTeamAverageSalary(),
                filter.getMedExamYear(),
                filter.getMedExamIsPassed(),
                pageable
        ).map(getMapper()::toDto);
    }

    @Override
    public Page<MedicalExaminationDto> getMedicalExaminations(Long employeeId, Pageable pageable) {
        return medicalExaminationRepository
                .getAllByEmployeeId(employeeId, pageable)
                .map(medExamMapper::toDto);
    }

    @Override
    protected JpaRepository<Employee, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Employee, EmployeeDto, Long> getMapper() {
        return mapper;
    }

    private String prepareStringToLikeStatement(String stringValue) {
        return stringValue == null ?
                null : String.format("%%%s%%", stringValue.toLowerCase());
    }
}

