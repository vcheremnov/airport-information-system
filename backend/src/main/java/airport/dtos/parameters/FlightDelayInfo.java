package airport.dtos.parameters;

import airport.entities.types.FlightDelayReason;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class FlightDelayInfo {

    Timestamp newFlightTime;
    FlightDelayReason delayReason;

}
