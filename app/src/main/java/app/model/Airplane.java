package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class Airplane extends Entity {

    private AirplaneType airplaneType;
    private Team pilotTeam;
    private Team techTeam;
    private Team serviceTeam;
    private Date commissioningDate;
    
    private String airplaneTypeProperty;
    private Long pilotTeamIdProperty;
    private Long techTeamIdProperty;
    private Long serviceTeamIdProperty;
    private String commissioningDateProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();
        airplaneTypeProperty = airplaneType.getName();
        pilotTeamIdProperty = pilotTeam.getId();
        techTeamIdProperty = techTeam.getId();
        serviceTeamIdProperty = serviceTeam.getId();
        commissioningDateProperty = LocalDateFormatter.getFormattedDate(commissioningDate);
    }
    
    private static final Map<String, String> propertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneTypeProperty", "Модель");
        propertyNames.put("pilotTeamIdProperty", "№ бригады пилотов");
        propertyNames.put("techTeamIdProperty", "№ бригады техников");
        propertyNames.put("serviceTeamIdProperty", "№ бригады обслуживания");
        propertyNames.put("commissioningDateProperty", "Введен в эксплуатацию");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
