package app.services;


import app.model.Flight;
import app.model.Ticket;
import app.model.parameters.FlightDelayInfo;
import app.model.types.FlightDelayReason;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

import java.sql.Timestamp;

public interface FlightService extends Service<Flight> {

    ServiceResponse<Flight> delayFlight(Long flightId, FlightDelayInfo flightDelayInfo);

    ServiceResponse<Page<Ticket>> getTickets(Long flightId, PageInfo pageInfo);

}
