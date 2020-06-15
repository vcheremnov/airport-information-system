package app.model.types;

import app.gui.custom.ChoiceItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FlightType {
    ARRIVAL,
    DEPARTURE;

    public static String toLocalizedString(FlightType flightType) {
        switch (flightType) {
            case ARRIVAL:
                return "Прибытие";
            case DEPARTURE:
                return "Вылет";
            default:
                return "";
        }
    }

    public static List<ChoiceItem<FlightType>> getChoiceItems() {
        return Arrays.stream(FlightType.values())
                .map(t -> new ChoiceItem<>(t, FlightType.toLocalizedString(t)))
                .collect(Collectors.toList());
    }
}
