package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class MedicalExaminationDto extends AbstractDto<Long> {

    private EmployeeDto employee;
    private Date examDate;
    private Boolean isPassed;

}
