package app.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class TechInspection extends Entity {

    private Long airplaneId;
    private Timestamp inspectionTime;
    private Boolean isPassed;

    private String inspectionTimeProperty;
    private String resultProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        inspectionTimeProperty = LocalDateFormatter.getFormattedTimestamp(inspectionTime);
        resultProperty = isPassed ? "Пройден" : "Не пройден";
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneId", "№ самолета");
        propertyNames.put("inspectionTimeProperty", "Время проведения");
        propertyNames.put("resultProperty", "Результат");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
