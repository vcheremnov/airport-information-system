package app.model;

import app.model.types.Attribute;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public abstract class Entity {

    private Long id;

    private static final Map<String, String> localizedFields = new LinkedHashMap<>();

    static {
        localizedFields.put("id", "#");
    }

    public static Map<String, String> getLocalizedFields() {
        return localizedFields;
    }

    public List<Attribute> getAttributes() {
        return List.of(
            new Attribute("ID", String.valueOf(id))
        );
    }

    public void fillAttributes(List<Attribute> attributes) {
        for (var attribute: attributes) {
            if ("ID".equals(attribute.getName())) {
                id = Long.parseLong(attribute.getValue());
            }
        }
    }

}
