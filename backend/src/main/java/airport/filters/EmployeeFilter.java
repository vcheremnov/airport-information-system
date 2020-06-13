package airport.filters;

import airport.entities.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class EmployeeFilter {

    private Sex sex;

    private String name;
    private String teamName;
    private String departmentName;

    private Date minBirthDate;
    private Date maxBirthDate;

    private Date minEmploymentDate;
    private Date maxEmploymentDate;

    private Integer minSalary;
    private Integer maxSalary;

    private Double minTeamAverageSalary;
    private Double maxTeamAverageSalary;

    private Integer medExamYear;
    private Boolean medExamIsPassed;

}
