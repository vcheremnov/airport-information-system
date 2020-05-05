package airport.filters;

import airport.entities.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class EmployeeFilter {

    private Sex sex;
    private Long departmentId;
    private Range<Date> birthDateRange;
    private Range<Date> employmentDateRange;
    private Range<Integer> salaryRange;

}
