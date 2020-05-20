package app.model;

import app.model.types.FlightType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Flight extends Entity {

    private Long airplaneId;
    private FlightType flightType;
    private Timestamp flightTime;
    private Boolean isCancelled;
    private Double duration;
    private Double ticketPrice;
    private City city;
    private FlightDelay flightDelay;

    private Long ticketsSold;
    private Long ticketsBooked;
    private Long ticketsReturned;

}
