package app.services.impl.api;

import app.model.Flight;
import app.model.Ticket;
import app.model.parameters.FlightDelayInfo;
import app.model.types.FlightDelayReason;
import app.services.pagination.Page;
import retrofit2.Call;
import retrofit2.http.*;

import java.sql.Timestamp;
import java.util.Map;

public interface FlightServiceApi extends CrudServiceApi {

    @PUT("flights/{flightId}/delay")
    Call<Flight> delayFlight(
            @Path("flightId") Long flightId,
            @Body FlightDelayInfo flightDelayInfo
    );

    @GET("flights/{flightId}/tickets")
    Call<Page<Ticket>> getTickets(
            @Path("flightId") Long flightId,
            @QueryMap Map<String, Object> pageInfo
    );

}
