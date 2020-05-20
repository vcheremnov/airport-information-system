package airport.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "team")
@Getter @Setter
public class Team extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Employee> employees;

}
