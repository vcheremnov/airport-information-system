package airport.filters;

import airport.entities.types.Sex;
import airport.entities.types.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class TicketFilter {

    private Long flightId;
    private Date minFlightDate;
    private Date maxFlightDate;

    private Double minPrice;
    private Double maxPrice;
    private TicketStatus ticketStatus;

    private Sex passengerSex;
    private Date minPassengerBirthDate;
    private Date maxPassengerBirthDate;

}
