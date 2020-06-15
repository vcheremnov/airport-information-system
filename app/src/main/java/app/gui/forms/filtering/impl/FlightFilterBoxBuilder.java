package app.gui.forms.filtering.impl;

import app.gui.controllers.FilterBoxController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.model.Flight;
import app.model.types.FlightDelayReason;
import app.model.types.FlightType;
import app.services.filters.Filter;
import app.services.filters.FlightFilter;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.List;

public class FlightFilterBoxBuilder extends AbstractFilterBoxBuilder<Flight> {

    @Override
    protected void fillFilterBox(FilterBoxController<Flight> controller, Filter<Flight> filter) {
        FlightFilter flightFilter = (FlightFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(10);

        int row = 0;
        controller.addLabel("№ самолёта:", 0, row, 2);
        controller.addIntegerField(
                val -> flightFilter.setAirplaneId(val == null ? null : Long.valueOf(val)),
                2, row, 2
        );
        controller.addLabel("Модель самолёта:", 4, row, 2);
        controller.addTextField(flightFilter::setAirplaneTypeName, 6, row, 4);

        ++row;
        controller.addLabel("Тип рейса:", 0, row, 2);
        controller.addChoiceBox(
                flightFilter::setFlightType, FlightType::getChoiceItems,
                2, row, 2
        );
        controller.addLabel("Город:", 4, row, 2);
        controller.addTextField(flightFilter::setCityName, 6, row, 4);

        ++row;
        controller.addLabel("Дата полёта:", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addDateField(flightFilter::setMinDate, 3, row, 3);
        controller.addLabel("до", 6, row, 1);
        controller.addDateField(flightFilter::setMaxDate, 7, row, 3);

        ++row;
        controller.addLabel("Длительность (мин.):", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addIntegerField(
                val -> flightFilter.setMinDuration(val / 60.), 3, row, 3
        );
        controller.addLabel("до", 6, row, 1);
        controller.addIntegerField(
                val -> flightFilter.setMaxDuration(val / 60.), 7, row, 3
        );

        ++row;
        controller.addLabel("Цена билета:", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addIntegerField(
                val -> flightFilter.setMinTicketPrice(val == null ? null : Double.valueOf(val)), 3, row, 3
        );
        controller.addLabel("до", 6, row, 1);
        controller.addIntegerField(
                val -> flightFilter.setMaxTicketPrice(val == null ? null : Double.valueOf(val)), 7, row, 3
        );

        ChoiceItemSupplier<Boolean> booleanItemSupplier = () -> {
            List<ChoiceItem<Boolean>> choiceItems = new ArrayList<>();
            choiceItems.add(new ChoiceItem<>(true, "Да"));
            choiceItems.add(new ChoiceItem<>(false, "Нет"));
            return choiceItems;
        };

        ++row;
        controller.addLabel("Отменен:", 0, row, 2);
        controller.addChoiceBox(
                flightFilter::setIsCancelled, booleanItemSupplier, 2, row, 1
        );
        controller.addLabel("Задержан:", 3, row, 2);
        controller.addChoiceBox(
                flightFilter::setIsDelayed, booleanItemSupplier, 5, row, 1
        );
        controller.addLabel("Причина задержки:", 6, row, 2);
        controller.addChoiceBox(
                flightFilter::setDelayReason, FlightDelayReason::getChoiceItems,
                8, row, 2
        );

    }

}
