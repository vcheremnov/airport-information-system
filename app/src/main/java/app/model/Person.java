package app.model;

import app.model.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class Person extends Entity {

    private String name;
    private Sex sex;
    private Date birthDate;
    
    private String birthDateProperty;
    private String sexProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        birthDateProperty = LocalDateFormatter.getFormattedDate(birthDate);
        sexProperty = Sex.toLocalizedString(sex);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "ФИО");
        propertyNames.put("sexProperty", "Пол");
        propertyNames.put("birthDateProperty", "Дата рождения");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
