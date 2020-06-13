package app.model;

import app.model.types.Sex;
import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.*;

@Getter @Setter
public class Person extends Entity {

    private String name;
    private Sex sex;
    private Date birthDate;
    
    private String birthDateProperty;
    private String sexProperty;

    @Override
    public Person clone() {
        return (Person) super.clone();
    }

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        birthDateProperty = LocalDateFormatter.getFormattedDate(birthDate);
        sexProperty = Sex.toLocalizedString(sex);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("name", "ФИО");
        propertyNames.put("sexProperty", "Пол");
        propertyNames.put("birthDateProperty", "Дата рождения");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("name", "ФИО");
        sortPropertyNames.put("birthDate", "Дата рождения");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
