package app.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Repair extends Entity {

    private Long airplaneId;
    private Timestamp startTime;
    private Timestamp finishTime;

}
