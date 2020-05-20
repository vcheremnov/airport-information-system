package app.services.impl.service;

import app.model.Flight;
import app.model.types.FlightDelayReason;
import app.services.AbstractCrudService;
import app.services.ServiceResponse;
import app.services.impl.api.FlightServiceApi;

import java.sql.Timestamp;

public class FlightService extends AbstractCrudService<Flight> {

    public FlightService() {
        super(FlightServiceApi.class, Flight.class, "flights");
    }

    ServiceResponse<Flight> delayFlight(Long flightId, Timestamp newFlightTime, FlightDelayReason reason) {
        var call = getServiceApi().delayFlight(flightId, newFlightTime, reason);
        return getServerResponse(call);
    }

    private FlightServiceApi getServiceApi() {
        return (FlightServiceApi) getCrudServiceApi();
    }

}
