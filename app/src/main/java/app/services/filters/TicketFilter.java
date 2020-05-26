package app.services.filters;

import app.model.Ticket;
import app.model.types.Sex;
import app.model.types.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class TicketFilter implements Filter<Ticket> {

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
