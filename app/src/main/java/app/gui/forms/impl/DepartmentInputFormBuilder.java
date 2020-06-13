package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.model.Department;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;

public class DepartmentInputFormBuilder extends AbstractEntityInputFormBuilder<Department> {

    public DepartmentInputFormBuilder(RequestExecutor requestExecutor) {
        super(Department::new, ServiceFactory.getDepartmentService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            Department department,
            FormType formType,
            boolean isContextWindow,
            EntityInputFormController<Department> controller
    ) {

        ChoiceItemSupplier<Long> chiefIdSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getChiefService(),
                c -> new ChoiceItem<>(c.getId(), c.getName()),
                "Не удалось загрузить список начальников"
        );

        if (formType == FormType.CREATION_FORM) {
            controller.addTextField(
                    "Название",
                    department.getName(),
                    department::setName
            );
        }

        controller.addChoiceBox(
                "Начальник",
                department.getChief().getId(),
                value -> department.getChief().setId(value),
                chiefIdSupplier
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый отдел";
    }

    @Override
    protected String getEditFormWindowTitle(Department department) {
        return String.format("%s - изменить", department.getName());
    }

}
