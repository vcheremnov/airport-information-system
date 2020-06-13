package app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;

@Getter @Setter
public class Department extends Entity {

    private String name;
    private Chief chief = new Chief();

    private String chiefNameProperty;

    @Override
    public Department clone() {
        var clone = (Department) super.clone();
        clone.setChief(chief.clone());
        return clone;
    }

    @Override
    public void calculateProperties() {
        super.calculateProperties();
        chiefNameProperty = chief.getName();
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Отдел");
        propertyNames.put("chiefNameProperty", "ФИО начальника");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "Название отдела");
        sortPropertyNames.put("chiefName", "ФИО начальника");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
