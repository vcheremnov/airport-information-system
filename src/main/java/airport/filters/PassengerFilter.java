package airport.filters;

import airport.entities.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class PassengerFilter {

    private Long flightId;
    private Date minFlightDate;
    private Date maxFlightDate;

    private Sex sex;
    private Date minBirthDate;
    private Date maxBirthDate;

}
