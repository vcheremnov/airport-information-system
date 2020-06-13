package app.model;

import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class TechInspection extends Entity {

    private Airplane airplane = new Airplane();
    private Date inspectionTime;
    private Boolean isPassed;

    private Long airplaneIdProperty;
    private String airplaneTypeNameProperty;
    private String inspectionTimeProperty;
    private String resultProperty;

    @Override
    public TechInspection clone() {
        var clone = (TechInspection) super.clone();
        clone.setAirplane(airplane.clone());
        return clone;
    }

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        airplaneIdProperty = airplane.getId();
        airplaneTypeNameProperty = airplane.getAirplaneType().getName();
        inspectionTimeProperty = LocalDateFormatter.getFormattedDateTime(inspectionTime);
        resultProperty = isPassed ? "Пройден" : "Не пройден";
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneIdProperty", "№ самолета");
        propertyNames.put("airplaneTypeNameProperty", "Название модели");
        propertyNames.put("inspectionTimeProperty", "Время проведения");
        propertyNames.put("resultProperty", "Результат");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("airplaneId", "№ самолета");
        sortPropertyNames.put("airplaneAirplaneTypeName", "Название модели");
        sortPropertyNames.put("inspectionTime", "Время проведения");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
