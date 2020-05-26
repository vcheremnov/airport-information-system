package app.services;

import app.model.Employee;
import app.model.MedicalExamination;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

public interface EmployeeService extends Service<Employee> {

    ServiceResponse<Page<Employee>> getByMedExamResult(
            Integer year, Boolean isPassed, PageInfo pageInfo
    );

    ServiceResponse<Page<MedicalExamination>> getMedicalExaminations(Long employeeId, PageInfo pageInfo);

}
