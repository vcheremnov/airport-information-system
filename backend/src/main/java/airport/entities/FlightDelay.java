package airport.entities;

import airport.entities.types.FlightDelayReason;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "flight_delay")
@Getter @Setter
public class FlightDelay extends AbstractEntity<Long> {
    @OneToOne
    @MapsId
    private Flight flight;

    @JoinColumn(name = "delay_reason")
    @Enumerated(EnumType.STRING)
    private FlightDelayReason delayReason;

}
