package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.model.Airplane;
import app.model.AirplaneType;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;
import javafx.stage.Stage;

public class AirplaneTypeInputFormBuilder
        extends AbstractEntityInputFormBuilder<AirplaneType> {

    public AirplaneTypeInputFormBuilder(RequestExecutor requestExecutor) {
        super(AirplaneType::new, ServiceFactory.getAirplaneTypeService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            AirplaneType airplaneType,
            FormType formType,
            EntityInputFormController<AirplaneType> controller
    ) {

        controller.addTextField(
                "Название",
                airplaneType.getName(),
                airplaneType::setName
        );

        controller.addIntegerField(
                "Вместимость (чел.)",
                airplaneType.getCapacity(),
                airplaneType::setCapacity
        );

        controller.addIntegerField(
                "Скорость",
                airplaneType.getSpeed(),
                airplaneType::setSpeed
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новую модель самолёта";
    }

    @Override
    protected String getEditFormWindowTitle(AirplaneType airplaneType) {
        return String.format("Модель \"%s\" - изменить", airplaneType.getName());
    }

}
