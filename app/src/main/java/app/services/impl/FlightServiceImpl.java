package app.services.impl;

import app.model.Flight;
import app.model.Ticket;
import app.model.parameters.FlightDelayInfo;
import app.model.types.FlightDelayReason;
import app.services.FlightService;
import app.services.ServiceResponse;
import app.services.impl.api.FlightServiceApi;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

import java.sql.Timestamp;

public class FlightServiceImpl
        extends AbstractCrudServiceImpl<Flight>
        implements FlightService {

    public FlightServiceImpl(String baseUrl) {
        super(FlightServiceApi.class, Flight.class, baseUrl, "flights");
    }

    @Override
    public ServiceResponse<Flight> delayFlight(
            Long flightId, FlightDelayInfo flightDelayInfo
    ) {
        var call = getServiceApi().delayFlight(flightId, flightDelayInfo);
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<Ticket>> getTickets(Long flightId, PageInfo pageInfo) {
        var call = getServiceApi().getTickets(flightId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    private FlightServiceApi getServiceApi() {
        return (FlightServiceApi) getCrudServiceApi();
    }

}
