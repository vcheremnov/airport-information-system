package app.gui.forms.input.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.model.TechInspection;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;

public class TechInspectionInputFormBuilder
        extends AbstractEntityInputFormBuilder<TechInspection> {

    public TechInspectionInputFormBuilder(RequestExecutor requestExecutor) {
        super(TechInspection::new, ServiceFactory.getTechInspectionService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            TechInspection techInspection,
            FormType formType,
            boolean isContextWindow,
            EntityInputFormController<TechInspection> controller
    ) {

        ChoiceItemSupplier<Long> airplaneIdSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getAirplaneService(),
                a -> new ChoiceItem<>(
                        a.getId(), String.format("№%d (%s)", a.getId(), a.getAirplaneType().getName())
                ),
                "Не удалось загрузить список самолётов"
        );

        if (formType == FormType.CREATION_FORM && !isContextWindow) {
            controller.addChoiceBox(
                    "Самолёт",
                    techInspection.getAirplane().getId(),
                    value -> techInspection.getAirplane().setId(value),
                    airplaneIdSupplier
            );
        }

        controller.addDateTimeField(
                "Время проведения",
                techInspection.getInspectionTime(),
                techInspection::setInspectionTime
        );

        controller.addCheckBox(
                "Пройден",
                techInspection.getIsPassed(),
                techInspection::setIsPassed
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый тех. осмотр самолёта";
    }

    @Override
    protected String getEditFormWindowTitle(TechInspection techInspection) {
        return String.format(
                "Тех. осмотр №%d (самолёт №%d) - изменить",
                techInspection.getId(),
                techInspection.getAirplane().getId()
        );
    }

}
