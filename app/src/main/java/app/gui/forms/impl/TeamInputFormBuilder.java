package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.model.Team;
import app.model.Team;
import app.services.pagination.PageInfo;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.stream.Collectors;

public class TeamInputFormBuilder extends AbstractEntityInputFormBuilder<Team> {

    public TeamInputFormBuilder(RequestExecutor requestExecutor) {
        super(Team::new, ServiceFactory.getTeamService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            Team team,
            FormType formType,
            EntityInputFormController<Team> controller
    ) {

        ChoiceItemSupplier<Long> departmentIdSupplier = makeChoiceItemSupplierFromEntities(
                ServiceFactory.getDepartmentService(),
                d -> new ChoiceItem<>(d.getId(), d.getName()),
                "Не удалось загрузить список отделов"
        );

        controller.addTextField(
                "Название",
                team.getName(),
                team::setName
        );

        controller.addChoiceBox(
                "Отдел",
                team.getDepartment().getId(),
                value -> team.getDepartment().setId(value),
                departmentIdSupplier
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новую бригаду";
    }

    @Override
    protected String getEditFormWindowTitle(Team team) {
        return String.format("Бригада %s - изменить", team.getName());
    }

}
