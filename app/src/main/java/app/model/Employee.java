package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

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

    static {
        propertyNames.putAll(Person.getPropertyNames());
        propertyNames.put("departmentId", "№ отдела");
        propertyNames.put("teamId", "№ бригады");
        propertyNames.put("employmentDateProperty", "Дата найма");
        propertyNames.put("salary", "Зарплата");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
