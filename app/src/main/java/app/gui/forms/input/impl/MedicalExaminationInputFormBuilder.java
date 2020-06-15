package app.gui.forms.input.impl;

import app.gui.controllers.EntityInputFormController;
import app.model.MedicalExamination;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;

public class MedicalExaminationInputFormBuilder
        extends AbstractEntityInputFormBuilder<MedicalExamination> {

    public MedicalExaminationInputFormBuilder(RequestExecutor requestExecutor) {
        super(
                MedicalExamination::new,
                ServiceFactory.getMedicalExaminationService(),
                requestExecutor
        );
    }

    @Override
    protected void fillInputForm(
            MedicalExamination medicalExamination,
            FormType formType,
            boolean isContextWindow, EntityInputFormController<MedicalExamination> controller
    ) {

        if (formType == FormType.CREATION_FORM && !isContextWindow) {
            Long employeeId = medicalExamination.getEmployee().getId();
            controller.addIntegerField(
                    "№ сотрудника",
                    employeeId == null ? 0 : employeeId.intValue(),
                    value -> medicalExamination.getEmployee().setId(value.longValue())
            );
        }

        controller.addDateField(
                "Дата проведения",
                medicalExamination.getExamDate(),
                medicalExamination::setExamDate
        );

        controller.addCheckBox(
                "Пройден",
                medicalExamination.getIsPassed(),
                medicalExamination::setIsPassed
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый мед. осмотр";
    }

    @Override
    protected String getEditFormWindowTitle(MedicalExamination medicalExamination) {
        return String.format(
                "Мед. осмотр №%d (%s) - изменить",
                medicalExamination.getId(),
                medicalExamination.getEmployee().getName()
        );
    }

}
