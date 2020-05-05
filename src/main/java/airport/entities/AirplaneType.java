package airport.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "airplane_type")
@Getter @Setter
public class AirplaneType extends AbstractEntity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "speed")
    private Integer speed;

    @OneToMany(mappedBy = "airplaneType", fetch = FetchType.LAZY)
    private List<Airplane> airplanes;

}
