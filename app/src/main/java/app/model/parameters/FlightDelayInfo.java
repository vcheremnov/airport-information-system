package app.model.parameters;

import app.model.Entity;
import app.model.types.FlightDelayReason;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FlightDelayInfo {

    Date newFlightTime;
    FlightDelayReason delayReason;

}
