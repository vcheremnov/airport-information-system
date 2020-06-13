package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.controllers.interfaces.SuccessAction;
import app.gui.custom.ChoiceItem;
import app.model.Employee;
import app.model.Team;
import app.model.types.Sex;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EmployeeInputFormBuilder extends AbstractEntityInputFormBuilder<Employee> {

    public EmployeeInputFormBuilder(RequestExecutor requestExecutor) {
        super(Employee::new, ServiceFactory.getEmployeeService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            Employee employee,
            FormType formType,
            boolean isContextWindow,
            EntityInputFormController<Employee> controller
    ) {

        ChoiceItemSupplier<Sex> sexChoiceItemSupplier = () -> Arrays.stream(Sex.values())
                .map(s -> new ChoiceItem<>(s, Sex.toLocalizedString(s)))
                .collect(Collectors.toList());

        ChoiceItemSupplier<Long> teamIdSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getTeamService(),
                t -> new ChoiceItem<>(t.getId(), t.getName()),
                "Не удалось загрузить список бригад"
        );

        controller.addTextField(
                "ФИО сотрудника",
                employee.getName(),
                employee::setName
        );

        controller.addChoiceBox(
                "Пол",
                employee.getSex(),
                employee::setSex,
                sexChoiceItemSupplier
        );

        controller.addDateField(
                "Дата рождения",
                employee.getBirthDate(),
                employee::setBirthDate
        );

        if (!isContextWindow) {
            controller.addChoiceBox(
                    "Бригада",
                    employee.getTeam().getId(),
                    value -> employee.getTeam().setId(value),
                    teamIdSupplier
            );
        }

        controller.addIntegerField(
                "Зарплата",
                employee.getSalary(),
                employee::setSalary
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить нового сотрудника";
    }

    @Override
    protected String getEditFormWindowTitle(Employee employee) {
        return String.format("Сотрудник %s - изменить", employee.getName());
    }

}
