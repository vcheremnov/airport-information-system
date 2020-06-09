package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter @Setter
public class EmployeeDto extends PersonDto {

    private Date employmentDate;
    private Integer salary;
    private TeamDto team;

}
