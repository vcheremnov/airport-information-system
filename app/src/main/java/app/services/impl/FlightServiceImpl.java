package app.services.impl;

import app.model.Flight;
import app.model.types.FlightDelayReason;
import app.services.FlightService;
import app.services.ServiceResponse;
import app.services.impl.api.FlightServiceApi;

import java.sql.Timestamp;

public class FlightServiceImpl
        extends AbstractCrudServiceImpl<Flight>
        implements FlightService {

    public FlightServiceImpl(String baseUrl) {
        super(FlightServiceApi.class, Flight.class, baseUrl, "flights");
    }

    @Override
    public ServiceResponse<Flight> delayFlight(
            Long flightId, Timestamp newFlightTime, FlightDelayReason reason
    ) {
        var call = getServiceApi().delayFlight(flightId, newFlightTime, reason);
        return getServerResponse(call);
    }

    private FlightServiceApi getServiceApi() {
        return (FlightServiceApi) getCrudServiceApi();
    }

}
