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
    private String pilotTeamNameProperty;
    private String techTeamNameProperty;
    private String serviceTeamNameProperty;
    private String commissioningDateProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();
        airplaneTypeProperty = airplaneType.getName();
        pilotTeamNameProperty = pilotTeam.getName();
        techTeamNameProperty = techTeam.getName();
        serviceTeamNameProperty = serviceTeam.getName();
        commissioningDateProperty = LocalDateFormatter.getFormattedDate(commissioningDate);
    }
    
    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneTypeProperty", "Название модели");
        propertyNames.put("pilotTeamNameProperty", "Бригада пилотов");
        propertyNames.put("techTeamNameProperty", "Бригада техников");
        propertyNames.put("serviceTeamNameProperty", "Бригада обслуживания");
        propertyNames.put("commissioningDateProperty", "Введен в эксплуатацию");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("airplaneTypeName", "Название модели");
        sortPropertyNames.put("pilotTeamName", "Бригада пилотов");
        sortPropertyNames.put("techTeamName", "Бригада техников");
        sortPropertyNames.put("serviceTeamName", "Бригада обслуживания");
        sortPropertyNames.put("commissioningDate", "Дата введения в эксплуатацию");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
