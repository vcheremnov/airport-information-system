package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter @Setter
public class AirplaneDto extends AbstractDto<Long> {

    private AirplaneTypeDto airplaneType;
    private TeamDto pilotTeam;
    private TeamDto techTeam;
    private TeamDto serviceTeam;
    private Date commissioningDate;

}
