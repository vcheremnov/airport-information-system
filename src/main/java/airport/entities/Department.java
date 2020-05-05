package airport.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "department")
@Getter @Setter
public class Department extends AbstractEntity<Long> {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "chief_id")
    private Chief chief;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Team> teams;

}

