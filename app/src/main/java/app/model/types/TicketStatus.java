package app.model.types;

public enum TicketStatus {
    BOOKED,
    SOLD,
    RETURNED;

    public static String toLocalizedString(TicketStatus TicketStatus) {
        switch (TicketStatus) {
            case BOOKED:
                return "Забронирован";
            case SOLD:
                return "Продан";
            case RETURNED:
                return "Возвращен";
            default:
                return "";
        }
    }
}
