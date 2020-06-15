package airport.filters;

import airport.entities.types.FlightDelayReason;
import airport.entities.types.FlightType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class FlightFilter {

    private Long airplaneId;
    private String airplaneTypeName;
    private String cityName;

    private FlightType flightType;
    private Boolean isCancelled;
    private Boolean isDelayed;
    private FlightDelayReason delayReason;

    private Date minDate;
    private Date maxDate;

    private Double minDuration;
    private Double maxDuration;

    private Double minTicketPrice;
    private Double maxTicketPrice;

}
