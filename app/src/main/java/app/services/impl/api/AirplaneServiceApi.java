package app.services.impl.api;

import app.model.Repair;
import app.model.TechInspection;
import app.services.CrudServiceApi;
import app.services.pagination.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface AirplaneServiceApi extends CrudServiceApi {

    @GET("airplanes/{id}/repairs")
    Call<Page<Repair>> getRepairs(
            @Path("id") Long airplaneId,
            @QueryMap Map<String, Object> pageInfo
    );

    @GET("airplanes/{id}/tech-inspections")
    Call<Page<TechInspection>> getTechInspections(
            @Path("id") Long airplaneId,
            @QueryMap Map<String, Object> pageInfo
    );

}
