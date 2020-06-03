package app.model.types;

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
}
