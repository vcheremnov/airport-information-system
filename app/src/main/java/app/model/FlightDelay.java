package app.model;

import app.model.types.FlightDelayReason;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FlightDelay extends Entity {

    private FlightDelayReason delayReason;

    @Override
    public FlightDelay clone() {
        return (FlightDelay) super.clone();
    }

}
