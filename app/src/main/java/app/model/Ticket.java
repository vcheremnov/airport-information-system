package app.model;

import app.model.types.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter @Setter
public class Ticket extends Entity {

    private Long flightId;
    private Passenger passenger;
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

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("flightId", "№ рейса");
        propertyNames.put("passengerNameProperty", "Пассажир");
        propertyNames.put("ticketStatusProperty", "Статус билета");
        propertyNames.put("priceProperty", "Цена");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
