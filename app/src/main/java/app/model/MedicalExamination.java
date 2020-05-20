package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class MedicalExamination extends Entity {

    private Long employeeId;
    private Date examDate;
    private Boolean isPassed;

}
