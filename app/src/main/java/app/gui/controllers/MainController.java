package app.gui.controllers;

import app.utils.LocalDateFormatter;
import app.utils.ServiceFactory;
import app.model.*;
import app.services.Service;
import app.utils.RequestExecutor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.net.URL;
import java.time.Instant;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MainController {

    private Stage stage;
    private RequestExecutor requestExecutor;

    private final Set<EntityTableController<?>> entityTableControllers = new HashSet<>();

    @FXML
    private TabPane contentTabPane;

    @FXML
    private Label statusBarLabel;

    @FXML
    private Tab defaultTab;

    @SneakyThrows
    public void init(
            Stage stage,
            RequestExecutor requestExecutor
    ) {
        this.stage = stage;
        this.requestExecutor = requestExecutor;
        contentTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    @FXML
    void openAirplaneTypes() {
        addTableTab(
                "Модели самолетов",
                AirplaneType.getPropertyNames(),
                AirplaneType.getSortPropertyNames(),
                ServiceFactory.getAirplaneTypeService()
        );
    }

    @FXML
    void openAirplanes() {
        addTableTab(
                "Самолеты",
                Airplane.getPropertyNames(),
                Airplane.getSortPropertyNames(),
                ServiceFactory.getAirplaneService()
        );
    }

    @FXML
    void openChiefs() {
        addTableTab(
                "Начальники",
                Chief.getPropertyNames(),
                Chief.getSortPropertyNames(),
                ServiceFactory.getChiefService()
        );
    }

    @FXML
    void openCities() {
        addTableTab(
                "Города",
                City.getPropertyNames(),
                City.getSortPropertyNames(),
                ServiceFactory.getCityService()
        );
    }

    @FXML
    void openDepartments() {
        addTableTab(
                "Отделы",
                Department.getPropertyNames(),
                Department.getSortPropertyNames(),
                ServiceFactory.getDepartmentService()
        );
    }

    @FXML
    void openEmployees() {
        addTableTab(
                "Сотрудники",
                Employee.getPropertyNames(),
                Employee.getSortPropertyNames(),
                ServiceFactory.getEmployeeService()
        );
    }

    @FXML
    void openFlights() {
        addTableTab(
                "Рейсы",
                Flight.getPropertyNames(),
                Flight.getSortPropertyNames(),
                ServiceFactory.getFlightService()
        );
    }

    @FXML
    void openMedExams() {
        addTableTab(
                "Мед. осмотры",
                MedicalExamination.getPropertyNames(),
                MedicalExamination.getSortPropertyNames(),
                ServiceFactory.getMedicalExaminationService()
        );
    }

    @FXML
    void openPassengers() {
        addTableTab(
                "Пассажиры",
                Passenger.getPropertyNames(),
                Passenger.getSortPropertyNames(),
                ServiceFactory.getPassengerService()
        );
    }

    @FXML
    void openRepairs() {
        addTableTab(
                "Ремонты",
                Repair.getPropertyNames(),
                Repair.getSortPropertyNames(),
                ServiceFactory.getRepairService()
        );
    }

    @FXML
    void openTeams() {
        addTableTab(
                "Бригады",
                Team.getPropertyNames(),
                Team.getSortPropertyNames(),
                ServiceFactory.getTeamService()
        );
    }

    @FXML
    void openTechInspections() {
        addTableTab(
                "Тех. осмотры",
                TechInspection.getPropertyNames(),
                TechInspection.getSortPropertyNames(),
                ServiceFactory.getTechInspectionService()
        );
    }

    @FXML
    void openTickets() {
        addTableTab(
                "Билеты",
                Ticket.getPropertyNames(),
                Ticket.getSortPropertyNames(),
                ServiceFactory.getTicketService()
        );
    }

    private void setStatusBarMessage(String message) {
        Platform.runLater(() -> {
            String messageTime = LocalDateFormatter.getFormattedTimestamp(Instant.now().toEpochMilli());
            String messageWithTime = String.format("%s: %s", messageTime, message);
            statusBarLabel.textProperty().setValue(messageWithTime);
        });
    }

    @SneakyThrows
    private <T extends Entity> void addTableTab(
            String tableName,
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            Service<T> entityService
    ) {
        FXMLLoader tableLoader = new FXMLLoader();
        URL entityTableLocation = getClass().getClassLoader().getResource("gui/entity_table.fxml");
        tableLoader.setLocation(entityTableLocation);
        Node table = tableLoader.load();

        Tab tableTab = new Tab(tableName);
        tableTab.setContent(table);
        tableTab.setOnClosed(event -> {
            if (contentTabPane.getTabs().isEmpty()) {
                contentTabPane.getTabs().add(defaultTab);
            }
        });

        contentTabPane.getTabs().remove(defaultTab);
        contentTabPane.getTabs().add(tableTab);
        contentTabPane.getSelectionModel().select(tableTab);

        EntityTableController<T> controller = tableLoader.getController();
        controller.init(
                entityPropertyNames,
                entitySortPropertyNames,
                requestExecutor,
                entityService,
                this::setStatusBarMessage
        );
        entityTableControllers.add(controller);
    }

}
