package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class RepairDto extends AbstractDto<Long> {

    private AirplaneDto airplane;
    private Timestamp startTime;
    private Timestamp finishTime;

}
