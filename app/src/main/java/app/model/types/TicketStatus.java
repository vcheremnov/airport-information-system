package app.model.types;

import app.gui.custom.ChoiceItem;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<ChoiceItem<TicketStatus>> getChoiceItems() {
        return Arrays.stream(TicketStatus.values())
                .map(s -> new ChoiceItem<>(s, TicketStatus.toLocalizedString(s)))
                .collect(Collectors.toList());
    }

}
