package app.model;

import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class Employee extends Person {

    private Date employmentDate;
    private Integer salary;
    private Long departmentId;
    private Long teamId;

    private String employmentDateProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();
        employmentDateProperty = LocalDateFormatter.getFormattedDate(employmentDate);
    }
    
    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Person.getPropertyNames());
        propertyNames.put("departmentId", "№ отдела");
        propertyNames.put("teamId", "№ бригады");
        propertyNames.put("employmentDateProperty", "Дата найма");
        propertyNames.put("salary", "Зарплата");

        sortPropertyNames.putAll(Person.getSortPropertyNames());
        sortPropertyNames.put("salary", "Зарплата");
        sortPropertyNames.put("teamId", "№ бригады");
        sortPropertyNames.put("employmentDate", "Дата найма");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
