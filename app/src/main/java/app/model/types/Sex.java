package app.model.types;

import app.gui.custom.ChoiceItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Sex {
    MALE,
    FEMALE;

    public static String toLocalizedString(Sex sex) {
        switch (sex) {
            case MALE:
                return "Муж.";
            case FEMALE:
                return "Жен.";
            default:
                return "";
        }
    }

    public static List<ChoiceItem<Sex>> getChoiceItems() {
        return Arrays.stream(Sex.values())
                .map(s -> new ChoiceItem<>(s, Sex.toLocalizedString(s)))
                .collect(Collectors.toList());
    }
}
