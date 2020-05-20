package app.services.impl.filter;

import app.model.Employee;
import app.model.types.Sex;
import app.services.Filter;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class EmployeeFilter implements Filter<Employee> {

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
