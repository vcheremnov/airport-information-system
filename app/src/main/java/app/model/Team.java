package app.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Team extends Entity {

    private Long departmentId;
    private Double averageSalary;

}
