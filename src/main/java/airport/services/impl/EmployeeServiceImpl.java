package airport.services.impl;

import airport.dtos.*;
import airport.entities.*;
import airport.filters.EmployeeFilter;
import airport.mappers.Mapper;
import airport.repositories.EmployeeRepository;
import airport.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl
        extends AbstractService<Employee, EmployeeDto, Long>
        implements EmployeeService {

    private final EmployeeRepository repository;
    private final Mapper<Employee, EmployeeDto, Long> mapper;
    private final Mapper<MedicalExamination, MedicalExaminationDto, Long> medExamMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository,
                               Mapper<Employee, EmployeeDto, Long> mapper,
                               Mapper<MedicalExamination, MedicalExaminationDto, Long> medExamMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.medExamMapper = medExamMapper;
    }

    @Override
    public Page<EmployeeDto> search(EmployeeFilter filter, Pageable pageable) {
        return repository.searchByFilter(
                filter.getSex(),
                filter.getDepartmentId(),
                filter.getMinBirthDate(),
                filter.getMaxBirthDate(),
                filter.getMinEmploymentDate(),
                filter.getMaxEmploymentDate(),
                filter.getMinSalary(),
                filter.getMaxSalary(),
                pageable
        ).map(getMapper()::toDto);
    }

    @Override
    public Page<EmployeeDto> getByMedExamResult(
            Integer year, Boolean isPassed, Pageable pageable
            ) {
        return repository
                .findEmployeesByMedExamResult(
                        year,
                        isPassed,
                        pageable
                ).map(getMapper()::toDto);
    }

    @Override
    public Collection<MedicalExaminationDto> getMedExams(Long employeeId) {
        return getEntityByIdOrThrow(employeeId)
                .getMedicalExaminations()
                .stream()
                .map(medExamMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    protected JpaRepository<Employee, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Employee, EmployeeDto, Long> getMapper() {
        return mapper;
    }
}

