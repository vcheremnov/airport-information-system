package app.services.impl.api;

import app.model.Flight;
import app.model.Ticket;
import app.model.types.FlightDelayReason;
import app.services.pagination.Page;
import retrofit2.Call;
import retrofit2.http.*;

import java.sql.Timestamp;
import java.util.Map;

public interface FlightServiceApi extends CrudServiceApi {

    @PUT("flights/{id}/delay")
    Call<Flight> delayFlight(
            @Path("id") Long flightId,
            @Query("flightTime") Timestamp newFlightTime,
            @Query("reason") FlightDelayReason reason
    );

    @GET("flights/{id}/tickets")
    Call<Page<Ticket>> getTickets(
            @Path("id") Long flightId,
            @QueryMap Map<String, Object> pageInfo
    );

}
