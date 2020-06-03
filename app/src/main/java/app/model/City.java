package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class City extends Entity {

    private String name;
    private Integer distance;

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Город");
        propertyNames.put("distance", "Расстояние (км)");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
