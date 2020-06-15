package airport.dtos;

import airport.entities.types.FlightType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class FlightDto extends AbstractDto<Long> {

    private AirplaneDto airplane;
    private FlightType flightType;
    private Timestamp flightTime;
    private Boolean isCancelled;
    private Double duration;
    private Double ticketPrice;
    private CityDto city;
    private FlightDelayDto flightDelay;

    private Long ticketsSold;
    private Long ticketsBooked;
    private Long ticketsReturned;

}
