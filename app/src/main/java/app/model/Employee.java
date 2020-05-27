package app.model;

import app.model.types.Attribute;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class Employee extends Person {

    private Date employmentDate;
    private Integer salary;
    private Long departmentId;
    private Long teamId;

    private static final Map<String, String> localizedFields = new LinkedHashMap<>();

    static {
        localizedFields.putAll(Person.getLocalizedFields());
        localizedFields.put("employmentDate", "Дата найма");
        localizedFields.put("salary", "Зарплата");
        localizedFields.put("departmentId", "Номер отдела");
        localizedFields.put("teamId", "Номер бригады");
    }

    public static Map<String, String> getLocalizedFields() {
        return localizedFields;
    }

    @Override
    public List<Attribute> getAttributes() {
        List<Attribute> attributes = super.getAttributes();
        attributes.addAll(List.of(
                new Attribute("Дата найма", String.valueOf(employmentDate)),
                new Attribute("Зарплата", String.valueOf(salary)),
                new Attribute("Номер отдела", String.valueOf(departmentId)),
                new Attribute("Номер команды", String.valueOf(teamId))
        ));

        return attributes;
    }

}
