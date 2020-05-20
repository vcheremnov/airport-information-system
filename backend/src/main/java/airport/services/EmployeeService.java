package airport.services;

import airport.dtos.EmployeeDto;
import airport.dtos.MedicalExaminationDto;
import airport.entities.Employee;
import airport.filters.EmployeeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.Date;

public interface EmployeeService extends Service<EmployeeDto, Long> {
    Page<EmployeeDto> search(EmployeeFilter filter, Pageable pageable);

    Page<EmployeeDto> getByMedExamResult(
            Integer year, Boolean isPassed, Pageable pageable
    );

    Collection<MedicalExaminationDto> getMedExams(Long employeeId);
}
