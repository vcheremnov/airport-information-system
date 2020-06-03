package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class Department extends Entity {

    private String name;
    private Chief chief;

    private String chiefNameProperty;
    
    @Override
    public void calculateProperties() {
        super.calculateProperties();
        chiefNameProperty = chief.getName();
    }
    
    private static final Map<String, String> propertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Отдел");
        propertyNames.put("chiefNameProperty", "Начальник");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
