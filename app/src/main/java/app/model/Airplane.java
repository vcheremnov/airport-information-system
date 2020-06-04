package app.model;

import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

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
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneTypeProperty", "Название модели");
        propertyNames.put("pilotTeamIdProperty", "№ бригады пилотов");
        propertyNames.put("techTeamIdProperty", "№ бригады техников");
        propertyNames.put("serviceTeamIdProperty", "№ бригады обслуживания");
        propertyNames.put("commissioningDateProperty", "Введен в эксплуатацию");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("airplaneTypeName", "Название модели");
        sortPropertyNames.put("pilotTeamId", "№ бригады пилотов");
        sortPropertyNames.put("techTeamId", "№ бригады техников");
        sortPropertyNames.put("serviceTeamId", "№ бригады обслуживания");
        sortPropertyNames.put("commissioningDate", "Дата введения в эксплуатацию");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
