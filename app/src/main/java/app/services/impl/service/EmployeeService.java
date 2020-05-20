package app.services.impl.service;

import app.model.Employee;
import app.model.MedicalExamination;
import app.services.AbstractCrudService;
import app.services.ServiceResponse;
import app.services.impl.api.EmployeeServiceApi;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

public class EmployeeService extends AbstractCrudService<Employee> {

    public EmployeeService() {
        super(EmployeeServiceApi.class, Employee.class, "employees");
    }

    ServiceResponse<Page<Employee>> getByMedExamResult(Integer year, Boolean isPassed, PageInfo pageInfo) {
        var call = getServiceApi().getByMedExamResult(year, isPassed, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    ServiceResponse<Page<MedicalExamination>> getMedicalExaminations(Long employeeId) {
        var call = getServiceApi().getMedicalExaminations(employeeId);
        return getServerResponse(call);
    }

    private EmployeeServiceApi getServiceApi() {
        return (EmployeeServiceApi) getCrudServiceApi();
    }

}
