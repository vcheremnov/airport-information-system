package app.model.types;

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
}
