package app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;

@Getter @Setter
public class City extends Entity {

    private String name;
    private Integer distance;

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    @Override
    public City clone() {
        return (City) super.clone();
    }

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Город");
        propertyNames.put("distance", "Расстояние (км)");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "Название города");
        sortPropertyNames.put("distance", "Расстояние");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
