package airport.filters;

import airport.entities.types.FlightType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class FlightFilter {

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

}
