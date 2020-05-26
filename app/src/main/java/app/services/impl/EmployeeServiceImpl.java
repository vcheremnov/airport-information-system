package app.services.impl;

import app.model.Employee;
import app.model.MedicalExamination;
import app.services.EmployeeService;
import app.services.ServiceResponse;
import app.services.impl.api.EmployeeServiceApi;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

public class EmployeeServiceImpl
        extends AbstractCrudServiceImpl<Employee>
        implements EmployeeService {

    public EmployeeServiceImpl(String baseUrl) {
        super(EmployeeServiceApi.class, Employee.class, baseUrl, "employees");
    }

    @Override
    public ServiceResponse<Page<Employee>> getByMedExamResult(
            Integer year, Boolean isPassed, PageInfo pageInfo
    ) {
        var call = getServiceApi().getByMedExamResult(year, isPassed, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<MedicalExamination>> getMedicalExaminations(
            Long employeeId, PageInfo pageInfo
    ) {
        var call = getServiceApi().getMedicalExaminations(employeeId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    private EmployeeServiceApi getServiceApi() {
        return (EmployeeServiceApi) getCrudServiceApi();
    }

}
