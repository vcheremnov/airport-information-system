package app.model;

import app.model.types.TicketStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Ticket extends Entity {

    private Long flightId;
    private Passenger passenger;
    private TicketStatus status;
    private Double price;

}
