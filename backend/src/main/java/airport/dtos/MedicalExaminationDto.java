package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class MedicalExaminationDto extends AbstractDto<Long> {

    private Long employeeId;
    private Date examDate;
    private Boolean isPassed;

}
