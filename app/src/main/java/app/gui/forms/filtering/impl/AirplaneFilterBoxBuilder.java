package app.gui.forms.filtering.impl;

import app.gui.controllers.FilterBoxController;
import app.model.Airplane;
import app.services.filters.AirplaneFilter;
import app.services.filters.Filter;

public class AirplaneFilterBoxBuilder extends AbstractFilterBoxBuilder<Airplane> {

    @Override
    protected void fillFilterBox(FilterBoxController<Airplane> controller, Filter<Airplane> filter) {
        AirplaneFilter airplaneFilter = (AirplaneFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(9);

        int row = 0;
        controller.addLabel("Название модели самолёта:", 0, row, 3);
        controller.addTextField(airplaneFilter::setAirplaneTypeName, 3, row, 6);

        ++row;
        controller.addLabel("Введён в эксплуатацию:", 0, row, 3);
        controller.addLabel("от", 3, row, 1);
        controller.addDateField(airplaneFilter::setMinCommissioningDate, 4, row, 2);
        controller.addLabel("до", 6, row, 1);
        controller.addDateField(airplaneFilter::setMaxCommissioningDate, 7, row, 2);

        ++row;
        controller.addLabel("Тех. осмотр проводился:", 0, row, 3);
        controller.addLabel("от", 3, row, 1);
        controller.addDateField(airplaneFilter::setMinTechInspectionDate, 4, row, 2);
        controller.addLabel("до", 6, row, 1);
        controller.addDateField(airplaneFilter::setMaxTechInspectionDate, 7, row, 2);

        ++row;
        controller.addLabel("Ремонтировался:", 0, row, 3);
        controller.addLabel("от", 3, row, 1);
        controller.addDateField(airplaneFilter::setMinRepairDate, 4, row, 2);
        controller.addLabel("до", 6, row, 1);
        controller.addDateField(airplaneFilter::setMaxRepairDate, 7, row, 2);

        ++row;
        controller.addLabel("Ремонтировался раз:", 0, row, 3);
        controller.addLabel("от", 3, row, 1);
        controller.addIntegerField(airplaneFilter::setMinRepairsNumber, 4, row, 2);
        controller.addLabel("до", 6, row, 1);
        controller.addIntegerField(airplaneFilter::setMaxRepairsNumber, 7, row, 2);
    }

}
