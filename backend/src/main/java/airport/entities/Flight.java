package airport.entities;

import airport.entities.types.FlightType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
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
    private List<Ticket> tickets = new ArrayList<>();

    @Formula(
            "(" +
            "select (c.distance)::float / at.speed * 10000.0 " +
            "from city c, airplane a, airplane_type at " +
            "where c.id = city_id " +
            "and a.id = airplane_id " +
            "and a.airplane_type_id = at.id " +
            ")"
    )
    private Double ticketPrice;

    @Formula(
            "(" +
            "select (c.distance)::float / at.speed " +
            "from city c, airplane a, airplane_type at " +
            "where c.id = city_id " +
            "and a.id = airplane_id " +
            "and a.airplane_type_id = at.id " +
            ")"
    )
    private Double duration;

}

