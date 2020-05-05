package airport.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "chief")
@Getter @Setter
public class Chief extends Person {

    @OneToMany(mappedBy = "chief", fetch = FetchType.LAZY)
    private List<Department> departments;

}
