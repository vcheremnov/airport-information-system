package app.model;

import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.*;

@Getter @Setter
public class Repair extends Entity {

    private Long airplaneId;
    private Timestamp startTime;
    private Timestamp finishTime;

    private String startTimeProperty;
    private String finishTimeProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        startTimeProperty = LocalDateFormatter.getFormattedTimestamp(startTime);
        finishTimeProperty = LocalDateFormatter.getFormattedTimestamp(finishTime);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneId", "№ самолета");
        propertyNames.put("startTimeProperty", "Время начала");
        propertyNames.put("finishTimeProperty", "Время окончания");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("airplaneId", "№ самолета");
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
