package app.services.impl.service;

import app.model.Department;
import app.model.Team;
import app.services.AbstractCrudService;
import app.services.ServiceResponse;
import app.services.impl.api.DepartmentServiceApi;
import app.services.pagination.Page;

public class DepartmentService extends AbstractCrudService<Department> {

    public DepartmentService() {
        super(DepartmentServiceApi.class, Department.class, "departments");
    }

    public ServiceResponse<Page<Team>> getTeams(Long departmentId) {
        var call = getServiceApi().getTeams(departmentId);
        return getServerResponse(call);
    }

    private DepartmentServiceApi getServiceApi() {
        return (DepartmentServiceApi) getCrudServiceApi();
    }

}
