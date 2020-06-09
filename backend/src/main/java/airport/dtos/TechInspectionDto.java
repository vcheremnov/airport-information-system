package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class TechInspectionDto extends AbstractDto<Long> {

    private AirplaneDto airplane;
    private Timestamp inspectionTime;
    private Boolean isPassed;

}
