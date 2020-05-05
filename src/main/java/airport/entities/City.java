package airport.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "city")
@Getter @Setter
public class City extends AbstractEntity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "distance")
    private Integer distance;

}
