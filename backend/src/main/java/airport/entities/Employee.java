package airport.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "employee")
@Getter @Setter
public class Employee extends Person {

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "employment_date")
    private Date employmentDate;

    @Column(name = "salary")
    private Integer salary;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<MedicalExamination> medicalExaminations;

}
