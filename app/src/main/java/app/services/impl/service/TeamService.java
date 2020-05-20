package app.services.impl.service;

import app.model.Employee;
import app.model.Team;
import app.services.AbstractCrudService;
import app.services.ServiceResponse;
import app.services.impl.api.TeamServiceApi;
import app.services.pagination.Page;

public class TeamService extends AbstractCrudService<Team> {

    public TeamService() {
        super(TeamServiceApi.class, Team.class, "teams");
    }

    ServiceResponse<Page<Employee>> getEmployees(Long teamId) {
        var call = getServiceApi().getEmployees(teamId);
        return getServerResponse(call);
    }

    private TeamServiceApi getServiceApi() {
        return (TeamServiceApi) getCrudServiceApi();
    }

}
