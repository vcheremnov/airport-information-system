package app.model;

import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.*;

@Getter @Setter
public class Repair extends Entity {

    private Airplane airplane;
    private Timestamp startTime;
    private Timestamp finishTime;

    private Long airplaneIdProperty;
    private String airplaneTypeNameProperty;
    private String startTimeProperty;
    private String finishTimeProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        airplaneIdProperty = airplane.getId();
        airplaneTypeNameProperty = airplane.getAirplaneType().getName();
        startTimeProperty = LocalDateFormatter.getFormattedTimestamp(startTime);
        finishTimeProperty = LocalDateFormatter.getFormattedTimestamp(finishTime);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneIdProperty", "№ самолета");
        propertyNames.put("airplaneTypeNameProperty", "Название модели");
        propertyNames.put("startTimeProperty", "Время начала");
        propertyNames.put("finishTimeProperty", "Время окончания");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("airplaneId", "№ самолета");
        sortPropertyNames.put("airplaneAirplaneTypeName", "Название модели");
        sortPropertyNames.put("startTime", "Время начала");
        sortPropertyNames.put("finishTime", "Время окончания");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
