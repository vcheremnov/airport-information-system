package app.services.impl.api;

import app.model.City;
import app.services.CrudServiceApi;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.Map;

public interface TicketServiceApi extends CrudServiceApi {

    @GET("tickets/average-sold-by-city")
    Call<Map<City, Double>> getAverageSoldByCity();

}
