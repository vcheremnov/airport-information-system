package app.services;


import app.model.Flight;
import app.model.types.FlightDelayReason;

import java.sql.Timestamp;

public interface FlightService extends Service<Flight> {

    ServiceResponse<Flight> delayFlight(Long flightId, Timestamp newFlightTime, FlightDelayReason reason);

}
