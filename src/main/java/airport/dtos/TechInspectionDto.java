package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class TechInspectionDto extends AbstractDto<Long> {

    private Long airplaneId;
    private Timestamp inspectionTime;
    private Boolean isPassed;

}
