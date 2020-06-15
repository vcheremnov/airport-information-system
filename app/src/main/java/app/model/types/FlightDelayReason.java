package app.model.types;

import app.gui.custom.ChoiceItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FlightDelayReason {
    WEATHER_CONDITIONS,
    TECH_PROBLEMS;

    public static String toLocalizedString(FlightDelayReason flightDelayReason) {
        switch (flightDelayReason) {
            case WEATHER_CONDITIONS:
                return "Погодные условия";
            case TECH_PROBLEMS:
                return "Технические проблемы";
            default:
                return "";
        }
    }

    public static List<ChoiceItem<FlightDelayReason>> getChoiceItems() {
        return Arrays.stream(FlightDelayReason.values())
                .map(r -> new ChoiceItem<>(r, FlightDelayReason.toLocalizedString(r)))
                .collect(Collectors.toList());
    }

}
