package app.model;

import app.model.types.Attribute;
import app.model.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class Person extends Entity {

    private String name;
    private Sex sex;
    private Date birthDate;

    private static final Map<String, String> localizedFields = new LinkedHashMap<>();

    static {
        localizedFields.putAll(Entity.getLocalizedFields());
        localizedFields.put("name", "ФИО");
        localizedFields.put("sex", "Пол");
        localizedFields.put("birthDate", "Дата рождения");
    }

    public static Map<String, String> getLocalizedFields() {
        return localizedFields;
    }

    @Override
    public List<Attribute> getAttributes() {
        List<Attribute> attributes = super.getAttributes();
        attributes.addAll(List.of(
                new Attribute("Имя", name),
                new Attribute("Дата рождения", String.valueOf(birthDate)),
                new Attribute("Пол", String.valueOf(sex))
        ));

        return attributes;
    }

}
