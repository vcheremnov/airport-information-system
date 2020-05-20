package app.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class Airplane extends Entity {

    private AirplaneType airplaneType;
    private Team pilotTeam;
    private Team techTeam;
    private Team serviceTeam;
    private Date commissioningDate;

}
