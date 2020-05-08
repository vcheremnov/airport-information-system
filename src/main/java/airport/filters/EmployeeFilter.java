package airport.filters;

import airport.entities.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class EmployeeFilter {

    private Sex sex;
    private Long departmentId;
    private Date minBirthDate;
    private Date maxBirthDate;
    private Date minEmploymentDate;
    private Date maxEmploymentDate;
    private Integer minSalary;
    private Integer maxSalary;
    private Double minTeamAverageSalary;
    private Double maxTeamAverageSalary;

}
