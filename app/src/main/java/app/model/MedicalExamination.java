package app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class MedicalExamination extends Entity {

    private Long employeeId;
    private Date examDate;
    private Boolean isPassed;

    private String examDateProperty;
    private String resultProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();
        examDateProperty = LocalDateFormatter.getFormattedDate(examDate);
        resultProperty = isPassed ? "Пройден" : "Не пройден";
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("employeeId", "№ работника");
        propertyNames.put("examDateProperty", "Дата проведения");
        propertyNames.put("resultProperty", "Результат");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
