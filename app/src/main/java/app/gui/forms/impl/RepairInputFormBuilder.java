package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.model.Repair;
import app.model.Repair;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;
import javafx.stage.Stage;

public class RepairInputFormBuilder extends AbstractEntityInputFormBuilder<Repair> {

    public RepairInputFormBuilder(RequestExecutor requestExecutor) {
        super(Repair::new, ServiceFactory.getRepairService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            Repair repair,
            FormType formType,
            EntityInputFormController<Repair> controller
    ) {

        ChoiceItemSupplier<Long> airplaneIdSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getAirplaneService(),
                a -> new ChoiceItem<>(
                        a.getId(), String.format("№%d (%s)", a.getId(), a.getAirplaneType().getName())
                ),
                "Не удалось загрузить список самолётов"
        );

        if (formType == FormType.CREATION_FORM) {
            controller.addChoiceBox(
                    "Самолёт",
                    repair.getAirplane().getId(),
                    value -> repair.getAirplane().setId(value),
                    airplaneIdSupplier
            );
        }

        controller.addDateTimeField(
                "Время начала",
                repair.getStartTime(),
                repair::setStartTime
        );

        controller.addDateTimeField(
                "Время окончания",
                repair.getFinishTime(),
                repair::setFinishTime
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый ремонт самолёта";
    }

    @Override
    protected String getEditFormWindowTitle(Repair repair) {
        return String.format(
                "Ремонт №%d (самолёт №%d) - изменить",
                repair.getId(),
                repair.getAirplane().getId()
        );
    }

}
