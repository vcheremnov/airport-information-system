package app.services.impl;

import app.model.Employee;
import app.model.Team;
import app.services.ServiceResponse;
import app.services.TeamService;
import app.services.impl.api.TeamServiceApi;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

public class TeamServiceImpl
        extends AbstractCrudServiceImpl<Team>
        implements TeamService {

    public TeamServiceImpl(String baseUrl) {
        super(TeamServiceApi.class, Team.class, baseUrl, "teams");
    }

    @Override
    public ServiceResponse<Page<Employee>> getEmployees(Long teamId, PageInfo pageInfo) {
        var call = getServiceApi().getEmployees(teamId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    private TeamServiceApi getServiceApi() {
        return (TeamServiceApi) getCrudServiceApi();
    }

}
