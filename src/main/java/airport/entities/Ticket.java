package airport.entities;

import airport.entities.types.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
@Getter @Setter
public class Ticket extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

}
