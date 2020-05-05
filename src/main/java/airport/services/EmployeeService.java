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
    Page<EmployeeDto> search(Pageable pageable, EmployeeFilter filter);

    Page<EmployeeDto> getByMedExamResult(
            Pageable pageable, Integer year, Boolean isPassed
    );

    Collection<MedicalExaminationDto> getMedExams(Long employeeId);
}
