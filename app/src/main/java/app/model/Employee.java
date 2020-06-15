package app.model;

import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;

@Getter @Setter
public class Employee extends Person {

    private Date employmentDate;
    private Integer salary;
    private Team team = new Team();

    private String employmentDateProperty;
    private String departmentNameProperty;
    private String teamNameProperty;

    @Override
    public Employee clone() {
        var clone = (Employee) super.clone();
        clone.setTeam(team.clone());
        return clone;
    }


    @Override
    public void calculateProperties() {
        super.calculateProperties();
        employmentDateProperty = LocalDateFormatter.getFormattedDate(employmentDate);
        teamNameProperty = team.getName();
        departmentNameProperty = team.getDepartment().getName();
    }
    
    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Person.getPropertyNames());
        propertyNames.put("departmentNameProperty", "Отдел");
        propertyNames.put("teamNameProperty", "Бригада");
        propertyNames.put("employmentDateProperty", "Дата найма");
        propertyNames.put("salary", "Зарплата");

        sortPropertyNames.putAll(Person.getSortPropertyNames());
        sortPropertyNames.put("employmentDate", "Дата найма");
        sortPropertyNames.put("salary", "Зарплата");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
