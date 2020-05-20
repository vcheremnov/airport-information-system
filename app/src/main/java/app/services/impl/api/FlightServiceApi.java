package app.services.impl.api;

import app.model.Flight;
import app.model.types.FlightDelayReason;
import app.services.CrudServiceApi;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.sql.Timestamp;

public interface FlightServiceApi extends CrudServiceApi {

    @PUT("flights/{id}/delay")
    Call<Flight> delayFlight(
            @Path("id") Long flightId,
            @Query("flightTime") Timestamp newFlightTime,
            @Query("reason") FlightDelayReason reason
    );

}
