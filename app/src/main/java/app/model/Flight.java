package app.model;

import app.model.types.FlightType;
import app.utils.LocalDateFormatter;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

@Getter @Setter
public class Flight extends Entity {

    private Long airplaneId;
    private FlightType flightType;
    private Date flightTime;
    private Boolean isCancelled;
    private Double duration;
    private Double ticketPrice;
    private City city = new City();
    private FlightDelay flightDelay;

    private Long ticketsSold;
    private Long ticketsBooked;
    private Long ticketsReturned;

    private String flightTypeProperty;
    private String flightTimeProperty;
    private String cityNameProperty;
    private String durationProperty;
    private String ticketPriceProperty;
    private String wasDelayedProperty;
    private String statusProperty;

    @Override
    public void calculateProperties() {
        super.calculateProperties();

        flightTypeProperty = FlightType.toLocalizedString(flightType);
        flightTimeProperty = LocalDateFormatter.getFormattedDateTime(flightTime);
        cityNameProperty = city.getName();

        int hoursDuration = duration.intValue();
        int minutesDuration = ((Double) ((duration - hoursDuration) * 60.0)).intValue();
        durationProperty = String.format("%d ч. %d мин.", hoursDuration, minutesDuration);

        wasDelayedProperty = flightDelay == null ? "нет" : "да";

        if (isCancelled) {
            statusProperty = "Отменен";
        } else if (flightTime.before(Timestamp.from(Instant.now()))) {
            statusProperty = "Состоялся";
        } else {
            statusProperty = "Ожидается";
        }
    }

    private static final Map<String, String> propertyNames = new LinkedHashMap<>();
    private static final Map<String, String> sortPropertyNames = new LinkedHashMap<>();

    static {
        propertyNames.putAll(Entity.getPropertyNames());
        propertyNames.put("airplaneId", "№ самолета");
        propertyNames.put("flightTypeProperty", "Тип");
        propertyNames.put("cityNameProperty", "Город");
        propertyNames.put("flightTimeProperty", "Время");
        propertyNames.put("durationProperty", "Длительность");
        propertyNames.put("statusProperty", "Статус");
        propertyNames.put("wasDelayedProperty", "Был задержан");

        sortPropertyNames.putAll(Entity.getSortPropertyNames());
        sortPropertyNames.put("airplaneId", "№ самолета");
        sortPropertyNames.put("cityName", "Название города");
        sortPropertyNames.put("flightTime", "Время");
    }

    public static Map<String, String> getPropertyNames() {
        return Collections.unmodifiableMap(propertyNames);
    }

    public static Map<String, String> getSortPropertyNames() {
        return Collections.unmodifiableMap(sortPropertyNames);
    }

}
