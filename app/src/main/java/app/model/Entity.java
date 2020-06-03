package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public abstract class Entity {

    private Long id;

    private static final Map<String, String> properties = new LinkedHashMap<>();

    static {
        properties.put("id", "№");
    }

    public static Map<String, String> getPropertyNames() {
        return properties;
    }

    public void calculateProperties() { }

}
