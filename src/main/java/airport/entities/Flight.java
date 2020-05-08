package airport.entities;

import airport.entities.types.FlightType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "flight")
@Getter @Setter
public class Flight extends AbstractEntity<Long> {

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private FlightType flightType;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "flight_time")
    private Timestamp flightTime;

    @Column(name = "is_cancelled")
    private Boolean isCancelled;

    @OneToOne(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FlightDelay flightDelay;

    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    @Transient
    private Double ticketPrice;

    @Transient
    private Double duration;

    @PostLoad
    private void postLoad() {
        Integer flightDistance = city.getDistance();
        Integer airplaneSpeed = airplane.getAirplaneType().getSpeed();

        duration = flightDistance / (double) airplaneSpeed;
        ticketPrice = duration * 10000.;
    }

}

