package app.gui.forms.filtering.impl;

import app.gui.controllers.FilterBoxController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.model.Employee;
import app.model.types.Sex;
import app.services.filters.EmployeeFilter;
import app.services.filters.Filter;
import javafx.geometry.Pos;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class EmployeeFilterBoxBuilder extends AbstractFilterBoxBuilder<Employee> {

    @Override
    protected void fillFilterBox(FilterBoxController<Employee> controller, Filter<Employee> filter) {
        EmployeeFilter employeeFilter = (EmployeeFilter) filter;
        controller.setNumberOfRows(3);
        controller.setNumberOfCols(10);

        int row = 0;
        controller.addLabel("ФИО сотрудника:", 0, row, 2);
        controller.addTextField(employeeFilter::setName, 2, row, 5);
        controller.addLabel("Пол:", 7, row, 1);
        controller.addChoiceBox(employeeFilter::setSex, Sex::getChoiceItems, 8, row, 2);

        ++row;
        controller.addLabel("Отдел:", 0, row, 2);
        controller.addTextField(employeeFilter::setDepartmentName, 2, row, 4);
        controller.addLabel("Бригада:", 6, row, 1);
        controller.addTextField(employeeFilter::setTeamName, 7, row, 3);

        ++row;
        controller.addLabel("Дата рождения:", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addDateField(employeeFilter::setMinBirthDate, 3, row, 3);
        controller.addLabel("до",6, row, 1);
        controller.addDateField(employeeFilter::setMaxBirthDate, 7, row, 3);

        ++row;
        controller.addLabel("Дата найма:", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addDateField(employeeFilter::setMinEmploymentDate, 3, row, 3);
        controller.addLabel("до",6, row, 1);
        controller.addDateField(employeeFilter::setMaxEmploymentDate, 7, row, 3);

        ++row;
        controller.addLabel("Зарплата:", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addIntegerField(employeeFilter::setMinSalary, 3, row, 3);
        controller.addLabel("до",6, row, 1);
        controller.addIntegerField(employeeFilter::setMaxSalary, 7, row, 3);

        ++row;
        controller.addLabel("Средняя з/п бригады:", 0, row, 2);
        controller.addLabel("от", 2, row, 1);
        controller.addIntegerField(
                val -> employeeFilter.setMinTeamAverageSalary(val == null ? null : Double.valueOf(val)), 3, row, 3
        );
        controller.addLabel("до",6, row, 1);
        controller.addIntegerField(
                val -> employeeFilter.setMaxTeamAverageSalary(val == null ? null : Double.valueOf(val)), 7, row, 3
        );

        ++row;
        ChoiceItemSupplier<Boolean> choiceItemSupplier = () -> {
            List<ChoiceItem<Boolean>> choiceItems = new ArrayList<>();
            choiceItems.add(new ChoiceItem<>(true, "Успешно"));
            choiceItems.add(new ChoiceItem<>(false, "Неуспешно"));
            return choiceItems;
        };
        controller.addLabel("Мед. осмотр, пройденный в", 0, row, 3);
        controller.addIntegerField(employeeFilter::setMedExamYear, 3, row, 3);
        controller.addLabel("году, был пройден",6, row, 2);
        controller.addChoiceBox(
                employeeFilter::setMedExamIsPassed, choiceItemSupplier, 8, row, 2
        );

    }

}
