package app.services.impl;

import app.model.Department;
import app.model.Team;
import app.services.DepartmentService;
import app.services.ServiceResponse;
import app.services.impl.api.DepartmentServiceApi;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

public class DepartmentServiceImpl
        extends AbstractCrudServiceImpl<Department>
        implements DepartmentService {

    public DepartmentServiceImpl() {
        super(DepartmentServiceApi.class, Department.class, "departments");
    }

    @Override
    public ServiceResponse<Page<Team>> getTeams(Long departmentId, PageInfo pageInfo) {
        var call = getServiceApi().getTeams(departmentId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    private DepartmentServiceApi getServiceApi() {
        return (DepartmentServiceApi) getCrudServiceApi();
    }

}
