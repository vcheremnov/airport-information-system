package app.services.filters;

import app.model.Flight;
import app.model.types.FlightDelayReason;
import app.model.types.FlightType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class FlightFilter implements Filter<Flight> {

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
