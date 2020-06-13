package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.model.Flight;
import app.model.types.FlightType;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FlightInputFormBuilder extends AbstractEntityInputFormBuilder<Flight> {

    public FlightInputFormBuilder(RequestExecutor requestExecutor) {
        super(Flight::new, ServiceFactory.getFlightService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            Flight flight,
            FormType formType,
            boolean isContextWindow,
            EntityInputFormController<Flight> controller
    ) {

        ChoiceItemSupplier<Long> airplaneIdSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getAirplaneService(),
                a -> new ChoiceItem<>(a.getId(), String.format(
                        "№%d (%s)", a.getId(), a.getAirplaneType().getName()
                )),
                "Не удалось загрузить список самолетов"
        );

        ChoiceItemSupplier<Long> cityIdSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getCityService(),
                c -> new ChoiceItem<>(c.getId(), c.getName()),
                "Не удалось загрузить список городов"
        );

        ChoiceItemSupplier<FlightType> flightTypeSupplier = () ->
                Arrays.stream(FlightType.values())
                        .map(t -> new ChoiceItem<>(t, FlightType.toLocalizedString(t)))
                        .collect(Collectors.toList());

        controller.addChoiceBox(
                "Самолёт",
                flight.getAirplaneId(),
                flight::setAirplaneId,
                airplaneIdSupplier
        );

        controller.addChoiceBox(
                "Город",
                flight.getCity().getId(),
                value -> flight.getCity().setId(value),
                cityIdSupplier
        );

        controller.addChoiceBox(
                "Тип рейса",
                flight.getFlightType(),
                flight::setFlightType,
                flightTypeSupplier
        );

        if (formType == FormType.CREATION_FORM) {
            controller.addDateTimeField(
                    "Время",
                    flight.getFlightTime(),
                    flight::setFlightTime
            );
        }

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый рейс";
    }

    @Override
    protected String getEditFormWindowTitle(Flight flight) {
        return String.format("Рейс №%d - изменить", flight.getId());
    }

}
