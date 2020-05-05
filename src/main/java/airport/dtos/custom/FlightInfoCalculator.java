package airport.dtos.custom;

import airport.entities.Flight;


public class FlightInfoCalculator {

    public static Integer getTicketPrice(Flight flight) {
        return (int) (10000. * getFlightDuration(flight) / 60.);
    }

    public static Integer getFlightDuration(Flight flight) {
        Integer speed = flight.getAirplane().getAirplaneType().getSpeed();
        Integer distance = flight.getCity().getDistance();

        return (int) (distance / (double) speed * 60);
    }
}
