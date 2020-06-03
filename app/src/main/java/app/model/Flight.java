package app.model;

import app.model.types.FlightDelayReason;
import app.model.types.FlightType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Getter @Setter
public class Flight extends Entity {

    private Long airplaneId;
    private FlightType flightType;
    private Timestamp flightTime;
    private Boolean isCancelled;
    private Double duration;
    private Double ticketPrice;
    private City city;
    private FlightDelay flightDelay;

    private Long ticketsSold;
    private Long ticketsBooked;
    private Long ticketsReturned;

    private String flightTypeProperty;
    private String flightTimeProperty;
    private String cityNameProperty;
    private String durationProperty;
    private String ticketPriceProperty;
    private String flightDelayReasonProperty;
    private String statusProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        flightTypeProperty = FlightType.toLocalizedString(flightType);
        flightTimeProperty = LocalDateFormatter.getFormattedTimestamp(flightTime);
        cityNameProperty = city.getName();

        int hoursDuration = duration.intValue();
        int minutesDuration = ((Double) ((duration - hoursDuration) * 60.0)).intValue();
        durationProperty = String.format("%d ч. %d мин.", hoursDuration, minutesDuration);

        ticketPriceProperty = String.format("%.2f", ticketPrice);

        flightDelayReasonProperty = flightDelay == null ?
                "" : FlightDelayReason.toLocalizedString(flightDelay.getDelayReason());

        if (isCancelled) {
            statusProperty = "Отменен";
        } else if (flightTime.before(Timestamp.from(Instant.now()))) {
            statusProperty = "Состоялся";
        } else if (flightDelay != null) {
            statusProperty = "Задержан";
        } else {
            statusProperty = "Ожидается";
        }
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneId", "№ самолета");
        propertyNames.put("flightTypeProperty", "Тип");
        propertyNames.put("cityNameProperty", "Город");
        propertyNames.put("flightTimeProperty", "Время");
        propertyNames.put("durationProperty", "Длительность");
        propertyNames.put("ticketPriceProperty", "Цена, р");
        propertyNames.put("statusProperty", "Статус");
        propertyNames.put("flightDelayReasonProperty", "Причина задержки");
    }

    public static Map<String, String> getPropertyNames() {
        return propertyNames;
    }

}
