package app.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class TechInspection extends Entity {

    private Long airplaneId;
    private Timestamp inspectionTime;
    private Boolean isPassed;

}
