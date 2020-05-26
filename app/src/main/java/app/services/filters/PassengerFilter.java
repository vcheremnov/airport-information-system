package app.services.filters;

import app.model.Passenger;
import app.model.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class PassengerFilter implements Filter<Passenger> {

    private Long flightId;
    private Date minFlightDate;
    private Date maxFlightDate;

    private Sex sex;
    private Date minBirthDate;
    private Date maxBirthDate;

}
