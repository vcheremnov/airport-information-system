package app.services;

import app.model.Employee;
import app.model.Team;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

public interface TeamService extends Service<Team> {

    ServiceResponse<Page<Employee>> getEmployees(Long teamId, PageInfo pageInfo);

}
