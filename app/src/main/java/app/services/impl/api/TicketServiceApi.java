package app.services.impl.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TicketServiceApi extends CrudServiceApi {

    @GET("tickets/average-sold-by-city")
    Call<Double> getAverageSoldByCity(@Query("cityId") Long cityId);

}
