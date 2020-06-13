package app.services.impl.api;

import app.model.Ticket;
import retrofit2.Call;
import retrofit2.http.*;

public interface TicketServiceApi extends CrudServiceApi {

    @GET("tickets/average-sold-by-city")
    Call<Double> getAverageSoldByCity(@Query("cityId") Long cityId);

    @POST("flights/{flightId}/tickets")
    Call<Ticket> addTicket(
            @Path("flightId") Long flightId,
            @Body Ticket ticket
    );

}
