package app.model;

import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.*;

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
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneId", "№ самолета");
        propertyNames.put("inspectionTimeProperty", "Время проведения");
        propertyNames.put("resultProperty", "Результат");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("airplaneId", "№ самолета");
        sortPropertyNames.put("inspectionTime", "Время проведения");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
