package airport.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "medical_examination")
@Getter @Setter
public class MedicalExamination extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "exam_date")
    private Date examDate;

    @Column(name = "is_passed")
    private Boolean isPassed;

}
