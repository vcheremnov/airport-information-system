package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class AirplaneType extends Entity {

    private String name;
    private Integer capacity;
    private Integer speed;

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Модель");
        propertyNames.put("capacity", "Вместимость");
        propertyNames.put("speed", "Скорость");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }
    
}
