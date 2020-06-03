package app.model;

import app.model.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

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

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneId", "№ самолета");
        propertyNames.put("startTimeProperty", "Время начала");
        propertyNames.put("finishTimeProperty", "Время окончания");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
