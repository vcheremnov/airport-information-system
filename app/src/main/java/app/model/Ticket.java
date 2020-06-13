package app.model;

import app.model.types.Sex;
import app.model.types.TicketStatus;
import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class Ticket extends Entity {

    private Long flightId;
    private Passenger passenger = new Passenger();
    private TicketStatus status;
    private Double price;

    private String passengerNameProperty;
    private String passengerSexProperty;
    private String passengerBirthDateProperty;
    private String ticketStatusProperty;
    private String priceProperty;

    @Override
    public Ticket clone() {
        var clone = (Ticket) super.clone();
        clone.setPassenger(passenger.clone());
        return clone;
    }

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        passengerNameProperty = passenger.getName();
        passengerSexProperty = Sex.toLocalizedString(passenger.getSex());
        passengerBirthDateProperty = LocalDateFormatter.getFormattedDate(passenger.getBirthDate());

        ticketStatusProperty = TicketStatus.toLocalizedString(status);
        priceProperty = String.format("%.2f", price);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("flightId", "№ рейса");
        propertyNames.put("passengerNameProperty", "ФИО пассажира");
        propertyNames.put("passengerSexProperty", "Пол");
        propertyNames.put("passengerBirthDateProperty", "Дата рождения");
        propertyNames.put("priceProperty", "Цена");
        propertyNames.put("ticketStatusProperty", "Статус билета");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("flightId", "№ рейса");
        sortPropertyNames.put("passengerName", "ФИО пассажира");
        sortPropertyNames.put("passengerBirthDate", "Дата рождения");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
