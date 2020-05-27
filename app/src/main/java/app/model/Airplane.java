package app.model;

import app.model.types.Attribute;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter @Setter
public class Airplane extends Entity {

    private AirplaneType airplaneType;
    private Team pilotTeam;
    private Team techTeam;
    private Team serviceTeam;
    private Date commissioningDate;

}
