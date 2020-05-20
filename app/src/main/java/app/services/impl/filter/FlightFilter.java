package app.services.impl.filter;

import app.model.Flight;
import app.model.types.FlightType;
import app.services.Filter;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class FlightFilter implements Filter<Flight> {

    private Long airplaneId;
    private FlightType flightType;
    private Long airplaneTypeId;
    private Long cityId;
    private Boolean isCancelled;
    private Boolean isDelayed;
    private String delayReason;

    private Date minDate;
    private Date maxDate;

    private Double minDuration;
    private Double maxDuration;

    private Double minTicketPrice;
    private Double maxTicketPrice;

    private Double minSoldSeatsPercentage;
    private Double maxSoldSeatsPercentage;

}
