package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Employee extends Person {

    private Date employmentDate;
    private Integer salary;
    private Long departmentId;
    private Long teamId;

}
