package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class Team extends Entity {

    private String name;
    private Department department = new Department();
    private Double averageSalary;

    private String departmentNameProperty;
    private String averageSalaryProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        averageSalaryProperty = String.format("%.2f", averageSalary);
        departmentNameProperty = department.getName();
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "Название");
        propertyNames.put("departmentNameProperty", "Отдел");
        propertyNames.put("averageSalaryProperty", "Средняя з/п");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "Название");
        sortPropertyNames.put("departmentName", "Отдел");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
