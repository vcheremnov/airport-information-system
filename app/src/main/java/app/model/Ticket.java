package app.model;

import app.model.types.TicketStatus;
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
    private String ticketStatusProperty;
    private String priceProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        passengerNameProperty = passenger.getName();
        ticketStatusProperty = TicketStatus.toLocalizedString(status);
        priceProperty = String.format("%.2f", price);
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("flightId", "№ рейса");
        propertyNames.put("passengerNameProperty", "ФИО пассажира");
        propertyNames.put("ticketStatusProperty", "Статус билета");
        propertyNames.put("priceProperty", "Цена");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("flightId", "№ рейса");
        sortPropertyNames.put("passengerName", "ФИО пассажира");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
