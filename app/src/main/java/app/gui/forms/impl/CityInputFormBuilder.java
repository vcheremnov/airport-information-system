package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.model.City;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;

public class CityInputFormBuilder extends AbstractEntityInputFormBuilder<City> {

    public CityInputFormBuilder(RequestExecutor requestExecutor) {
        super(City::new, ServiceFactory.getCityService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            City city,
            FormType formType,
            boolean isContextWindow,
            EntityInputFormController<City> controller
    ) {

        controller.addTextField(
                "Название",
                city.getName(),
                city::setName
        );

        controller.addIntegerField(
                "Расстояние (км)",
                city.getDistance(),
                city::setDistance
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый город";
    }

    @Override
    protected String getEditFormWindowTitle(City city) {
        return String.format("Город %s - изменить", city.getName());
    }

}
