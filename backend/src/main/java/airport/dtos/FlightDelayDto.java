package airport.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FlightDelayDto extends AbstractDto<Long> {

    private String delayReason;

}
