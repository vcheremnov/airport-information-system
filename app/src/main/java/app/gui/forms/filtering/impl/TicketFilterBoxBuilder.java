package app.gui.forms.filtering.impl;

import app.gui.controllers.FilterBoxController;
import app.model.Airplane;
import app.model.Flight;
import app.model.Ticket;
import app.model.types.Sex;
import app.model.types.TicketStatus;
import app.services.filters.EmployeeFilter;
import app.services.filters.Filter;
import app.services.filters.FlightFilter;
import app.services.filters.TicketFilter;
import javafx.scene.Node;

public class TicketFilterBoxBuilder extends AbstractFilterBoxBuilder<Ticket> {

    @Override
    protected void fillFilterBox(FilterBoxController<Ticket> controller, Filter<Ticket> filter) {
        TicketFilter ticketFilter = (TicketFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(10);

        int row = 0;
        controller.addLabel("№ рейса:", 0, row, 1);
        controller.addIntegerField(
                val -> ticketFilter.setFlightId(val == null ? null : Long.valueOf(val)), 1, row, 2
        );
        controller.addLabel("Статус:", 3, row, 1);
        controller.addChoiceBox(ticketFilter::setTicketStatus, TicketStatus::getChoiceItems, 4, row, 2);
        controller.addLabel("Пол пассажира:", 6, row, 2);
        controller.addChoiceBox(ticketFilter::setPassengerSex, Sex::getChoiceItems, 8, row, 2);

        ++row;
        controller.addLabel("Дата рождения:", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addDateField(ticketFilter::setMinPassengerBirthDate, 3, row, 3);
        controller.addLabel("до", 6, row, 1);
        controller.addDateField(ticketFilter::setMinPassengerBirthDate, 7, row, 3);

        ++row;
        controller.addLabel("Дата полёта:", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addDateField(ticketFilter::setMinFlightDate, 3, row, 3);
        controller.addLabel("до", 6, row, 1);
        controller.addDateField(ticketFilter::setMaxFlightDate, 7, row, 3);

        ++row;
        controller.addLabel("Цена билета:", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addIntegerField(
                val -> ticketFilter.setMinPrice(val == null ? null : Double.valueOf(val)), 3, row, 3
        );
        controller.addLabel("до", 6, row, 1);
        controller.addIntegerField(
                val -> ticketFilter.setMaxPrice(val == null ? null : Double.valueOf(val)), 7, row, 3
        );
    }

}
