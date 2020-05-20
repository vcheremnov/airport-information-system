package airport.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tech_inspection")
@Getter @Setter
public class TechInspection extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;

    @Column(name = "inspection_time")
    private Timestamp inspectionTime;

    @Column(name = "is_passed")
    private Boolean isPassed;

}
