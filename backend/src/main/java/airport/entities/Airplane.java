package airport.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airplane")
@Getter @Setter
public class Airplane extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "airplane_type_id")
    private AirplaneType airplaneType;

    @ManyToOne
    @JoinColumn(name = "pilot_team_id")
    private Team pilotTeam;

    @ManyToOne
    @JoinColumn(name = "tech_team_id")
    private Team techTeam;

    @ManyToOne
    @JoinColumn(name = "service_team_id")
    private Team serviceTeam;

    @Column(name = "commissioning_date")
    private Date commissioningDate;

    @OneToMany(mappedBy = "airplane", fetch = FetchType.LAZY)
    private List<Repair> repairs = new ArrayList<>();

    @OneToMany(mappedBy = "airplane", fetch = FetchType.LAZY)
    private List<TechInspection> techInspections = new ArrayList<>();

    @OneToMany(mappedBy = "airplane", fetch = FetchType.LAZY)
    private List<Flight> flights = new ArrayList<>();

}
