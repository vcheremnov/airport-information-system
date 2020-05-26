package app.services;

import app.model.Department;
import app.model.Team;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

public interface DepartmentService extends Service<Department> {

    ServiceResponse<Page<Team>> getTeams(Long departmentId, PageInfo pageInfo);

}
