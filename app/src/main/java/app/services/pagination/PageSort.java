package app.services.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class PageSort {

    public enum Order {
        ASC,
        DESC
    }

    @Getter @Setter
    private Order order = Order.ASC;

    private final Set<String> fieldNameSet = new HashSet<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (var fieldName: fieldNameSet) {
            stringBuilder.append(fieldName);
            stringBuilder.append(',');
        }
        stringBuilder.append(order);
        return stringBuilder.toString();
    }

    public void addField(String fieldName) {
        fieldNameSet.add(fieldName);
    }

    public void removeField(String fieldName) {
        fieldNameSet.remove(fieldName);
    }

    public void removeAllFields() {
        fieldNameSet.clear();
    }

}
