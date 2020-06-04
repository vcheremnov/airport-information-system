package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class Team extends Entity {

    private Long departmentId;
    private Double averageSalary;

    private String averageSalaryProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        averageSalaryProperty = String.format("%.2f", averageSalary);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("departmentId", "№ отдела");
        propertyNames.put("averageSalaryProperty", "Средняя з/п");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("departmentId", "№ отдела");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
