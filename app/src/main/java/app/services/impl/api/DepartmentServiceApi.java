package app.services.impl.api;

import app.model.Team;
import app.services.CrudServiceApi;
import app.services.pagination.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface DepartmentServiceApi extends CrudServiceApi {

    @GET("departments/{id}/teams")
    Call<Page<Team>> getTeams(@Path("id") Long departmentId);

}
