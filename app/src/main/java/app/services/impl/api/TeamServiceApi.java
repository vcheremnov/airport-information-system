package app.services.impl.api;

import app.model.Employee;
import app.services.CrudServiceApi;
import app.services.pagination.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TeamServiceApi extends CrudServiceApi {

    @GET("teams/{id}/employees")
    Call<Page<Employee>> getEmployees(
        @Path("id") Long teamId
    );

}
