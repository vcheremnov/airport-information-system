package app.model;

import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class MedicalExamination extends Entity {

    private Employee employee;
    private Date examDate;
    private Boolean isPassed;

    private String employeeNameProperty;
    private String examDateProperty;
    private String resultProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();
        employeeNameProperty = employee.getName();
        examDateProperty = LocalDateFormatter.getFormattedDate(examDate);
        resultProperty = isPassed ? "Пройден" : "Не пройден";
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("employeeNameProperty", "ФИО работника");
        propertyNames.put("examDateProperty", "Дата проведения");
        propertyNames.put("resultProperty", "Результат");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("employeeName", "ФИО работника");
        sortPropertyNames.put("examDate", "Дата проведения");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
