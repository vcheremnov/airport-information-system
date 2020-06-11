package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.model.Airplane;
import app.services.AirplaneTypeService;
import app.services.TeamService;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;

public class AirplaneInputFormBuilder extends AbstractEntityInputFormBuilder<Airplane> {

    public AirplaneInputFormBuilder(RequestExecutor requestExecutor) {
        super(Airplane::new, ServiceFactory.getAirplaneService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            Airplane airplane,
            FormType formType,
            EntityInputFormController<Airplane> controller
    ) {

        AirplaneTypeService airplaneTypeService = ServiceFactory.getAirplaneTypeService();
        TeamService teamService = ServiceFactory.getTeamService();

        ChoiceItemSupplier<Long> airplaneTypeIdSupplier = makeChoiceItemSupplierFromEntities(
                airplaneTypeService,
                t -> new ChoiceItem<>(t.getId(), t.getName()),
                "Не удалось загрузить список моделей самолетов"
        );

        ChoiceItemSupplier<Long> pilotTeamIdSupplier = makeChoiceItemSupplierFromEntities(
                teamService,
                t -> t.getDepartment().getName().equals("Лётный отдел"),
                t -> new ChoiceItem<>(t.getId(), t.getName()),
                "Не удалось загрузить список бригад"
        );

        ChoiceItemSupplier<Long> techTeamIdSupplier = makeChoiceItemSupplierFromEntities(
                teamService,
                t -> t.getDepartment().getName().equals("Технический отдел"),
                t -> new ChoiceItem<>(t.getId(), t.getName()),
                "Не удалось загрузить список бригад"
        );

        ChoiceItemSupplier<Long> serviceTeamIdSupplier = makeChoiceItemSupplierFromEntities(
                teamService,
                t -> t.getDepartment().getName().equals("Отдел обслуживания"),
                t -> new ChoiceItem<>(t.getId(), t.getName()),
                "Не удалось загрузить список бригад"
        );

        controller.addChoiceBox(
                "Модель",
                airplane.getAirplaneType().getId(),
                value -> airplane.getAirplaneType().setId(value),
                airplaneTypeIdSupplier
        );

        controller.addChoiceBox(
                "Бригада пилотов",
                airplane.getPilotTeam().getId(),
                value -> airplane.getPilotTeam().setId(value),
                pilotTeamIdSupplier
        );

        controller.addChoiceBox(
                "Бригада техников",
                airplane.getTechTeam().getId(),
                value -> airplane.getTechTeam().setId(value),
                techTeamIdSupplier
        );

        controller.addChoiceBox(
                "Бригада обслуживания",
                airplane.getServiceTeam().getId(),
                value -> airplane.getServiceTeam().setId(value),
                serviceTeamIdSupplier
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый самолёт";
    }

    @Override
    protected String getEditFormWindowTitle(Airplane airplane) {
        return String.format(
                "Самолёт №%d (%s) - изменить",
                airplane.getId(),
                airplane.getAirplaneType().getName()
        );
    }

}
