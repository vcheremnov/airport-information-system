package airport.filters;

import airport.entities.types.FlightType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class FlightFilter {

    private FlightType flightType;
    private Long airplaneId;
    private Long airplaneTypeId;
    private Long cityId;
    private Boolean isCancelled;
    private Boolean isDelayed;
    private String delayReason;
    private Date minFlightDate;
    private Date maxFlightDate;

}
