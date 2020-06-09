package app.gui.controllers;

import app.services.*;
import app.utils.LocalDateFormatter;
import app.utils.ServiceFactory;
import app.model.*;
import app.utils.RequestExecutor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainController {

    private RequestExecutor requestExecutor;

    @FXML
    private TabPane contentTabPane;

    @FXML
    private Label statusBarLabel;

    @FXML
    private Tab defaultTab;

    @SneakyThrows
    public void init(
            RequestExecutor requestExecutor
    ) {
        this.requestExecutor = requestExecutor;
        contentTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    @FXML
    void openAirplaneTypes() {
        createEntityTable(
                "Модели самолетов",
                AirplaneType.getPropertyNames(),
                AirplaneType.getSortPropertyNames(),
                ServiceFactory.getAirplaneTypeService(),
                null
        );
    }

    @FXML
    @SneakyThrows
    void openAirplanes() {
        AirplaneService airplaneService = ServiceFactory.getAirplaneService();
        EntityTableController.InfoWindowBuilder<Airplane> infoWindowBuilder = airplane -> {

            var techInspectionPropertyNames = new LinkedHashMap<>(TechInspection.getPropertyNames());
            techInspectionPropertyNames.remove("airplaneIdProperty");
            techInspectionPropertyNames.remove("airplaneTypeNameProperty");

            var techInspectionSortPropertyNames = new LinkedHashMap<>(TechInspection.getSortPropertyNames());
            techInspectionSortPropertyNames.remove("airplaneId");
            techInspectionSortPropertyNames.remove("airplaneAirplaneTypeName");

            Node techInspectionsTable = createReadOnlyEntityTable(
                    techInspectionPropertyNames,
                    techInspectionSortPropertyNames,
                    (pageInfo, filter) -> airplaneService
                        .getTechInspections(airplane.getId(), pageInfo)
                        .map(page -> page.map(Entity.class::cast))
            );

            var repairPropertyNames = new LinkedHashMap<>(Repair.getPropertyNames());
            repairPropertyNames.remove("airplaneIdProperty");
            repairPropertyNames.remove("airplaneTypeNameProperty");

            var repairSortPropertyNames = new LinkedHashMap<>(Repair.getSortPropertyNames());
            repairSortPropertyNames.remove("airplaneId");
            repairSortPropertyNames.remove("airplaneAirplaneTypeName");

            Node repairTable = createReadOnlyEntityTable(
                    repairPropertyNames,
                    repairSortPropertyNames,
                    (pageInfo, filter) -> airplaneService
                            .getRepairs(airplane.getId(), pageInfo)
                            .map(page -> page.map(Entity.class::cast))
            );

            TabPane tabPane = new TabPane();
            addNodeToTabPane(tabPane, techInspectionsTable, "Тех. осмотры");
            addNodeToTabPane(tabPane, repairTable, "Ремонты");
            return createWindow(String.format("Самолёт №%d", airplane.getId()), tabPane);
        };

        createEntityTable(
                "Самолеты",
                Airplane.getPropertyNames(),
                Airplane.getSortPropertyNames(),
                ServiceFactory.getAirplaneService(),
                infoWindowBuilder
        );
    }

    @FXML
    void openChiefs() {
        createEntityTable(
                "Начальники",
                Chief.getPropertyNames(),
                Chief.getSortPropertyNames(),
                ServiceFactory.getChiefService(),
                null
        );
    }

    @FXML
    void openCities() {
        createEntityTable(
                "Города",
                City.getPropertyNames(),
                City.getSortPropertyNames(),
                ServiceFactory.getCityService(),
                null
        );
    }

    @FXML
    @SneakyThrows
    void openDepartments() {
        DepartmentService departmentService = ServiceFactory.getDepartmentService();
        EntityTableController.InfoWindowBuilder<Department> infoWindowBuilder = department -> {
            var teamPropertyNames = new LinkedHashMap<>(Team.getPropertyNames());
            teamPropertyNames.remove("departmentNameProperty");
            var teamSortPropertyNames = new LinkedHashMap<>(Team.getSortPropertyNames());
            teamSortPropertyNames.remove("departmentName");

            Node teamsTable = createReadOnlyEntityTable(
                    teamPropertyNames,
                    teamSortPropertyNames,
                    (pageInfo, filter) -> departmentService
                            .getTeams(department.getId(), pageInfo)
                            .map(page -> page.map(Entity.class::cast))
            );

            TabPane tabPane = new TabPane();
            addNodeToTabPane(tabPane, teamsTable, "Бригады");
            return createWindow(department.getName(), tabPane);
        };

        createEntityTable(
                "Отделы",
                Department.getPropertyNames(),
                Department.getSortPropertyNames(),
                ServiceFactory.getDepartmentService(),
                infoWindowBuilder
        );
    }

    @FXML
    @SneakyThrows
    void openEmployees() {
        EmployeeService employeeService = ServiceFactory.getEmployeeService();
        EntityTableController.InfoWindowBuilder<Employee> infoWindowBuilder = employee -> {
            var medExamPropertyNames = new LinkedHashMap<>(MedicalExamination.getPropertyNames());
            medExamPropertyNames.remove("employeeNameProperty");
            var medExamSortPropertyNames = new LinkedHashMap<>(MedicalExamination.getSortPropertyNames());
            medExamSortPropertyNames.remove("employeeName");

            Node medExamTable = createReadOnlyEntityTable(
                    medExamPropertyNames,
                    medExamSortPropertyNames,
                    (pageInfo, filter) -> employeeService
                            .getMedicalExaminations(employee.getId(), pageInfo)
                            .map(page -> page.map(Entity.class::cast))
            );

            TabPane tabPane = new TabPane();
            addNodeToTabPane(tabPane, medExamTable, "Мед. осмотры");
            return createWindow(String.format("Работник %s", employee.getName()), tabPane);
        };

        createEntityTable(
                "Сотрудники",
                Employee.getPropertyNames(),
                Employee.getSortPropertyNames(),
                ServiceFactory.getEmployeeService(),
                infoWindowBuilder
        );
    }

    @FXML
    void openFlights() {
        FlightService flightService = ServiceFactory.getFlightService();
        EntityTableController.InfoWindowBuilder<Flight> infoWindowBuilder = flight -> {
            var ticketPropertyNames = new LinkedHashMap<>(Ticket.getPropertyNames());
            ticketPropertyNames.remove("flightId");
            ticketPropertyNames.remove("priceProperty");
            var ticketSortPropertyNames = new LinkedHashMap<>(Ticket.getSortPropertyNames());
            ticketSortPropertyNames.remove("flightId");

            Node ticketsTable = createReadOnlyEntityTable(
                    ticketPropertyNames,
                    ticketSortPropertyNames,
                    (pageInfo, filter) -> flightService
                            .getTickets(flight.getId(), pageInfo)
                            .map(page -> page.map(Entity.class::cast))
            );

            TabPane tabPane = new TabPane();
            addNodeToTabPane(tabPane, ticketsTable, "Билеты");
            return createWindow(String.format("Рейс №%d", flight.getId()), tabPane);
        };

        createEntityTable(
                "Рейсы",
                Flight.getPropertyNames(),
                Flight.getSortPropertyNames(),
                ServiceFactory.getFlightService(),
                infoWindowBuilder
        );
    }

    @FXML
    void openMedExams() {
        createEntityTable(
                "Мед. осмотры",
                MedicalExamination.getPropertyNames(),
                MedicalExamination.getSortPropertyNames(),
                ServiceFactory.getMedicalExaminationService(),
                null
        );
    }

    @FXML
    void openPassengers() {
        createEntityTable(
                "Пассажиры",
                Passenger.getPropertyNames(),
                Passenger.getSortPropertyNames(),
                ServiceFactory.getPassengerService(),
                null
        );
    }

    @FXML
    void openRepairs() {
        createEntityTable(
                "Ремонты",
                Repair.getPropertyNames(),
                Repair.getSortPropertyNames(),
                ServiceFactory.getRepairService(),
                null
        );
    }

    @FXML
    @SneakyThrows
    void openTeams() {
        TeamService teamService = ServiceFactory.getTeamService();
        EntityTableController.InfoWindowBuilder<Team> infoWindowBuilder = team -> {
            var employeePropertyNames = new LinkedHashMap<>(Employee.getPropertyNames());
            employeePropertyNames.remove("departmentNameProperty");
            employeePropertyNames.remove("teamNameProperty");
            var employeeSortPropertyNames = new LinkedHashMap<>(Employee.getSortPropertyNames());
            employeeSortPropertyNames.remove("teamDepartmentName");
            employeeSortPropertyNames.remove("teamName");

            Node employeeTable = createReadOnlyEntityTable(
                    employeePropertyNames,
                    employeeSortPropertyNames,
                    (pageInfo, filter) -> teamService
                            .getEmployees(team.getId(), pageInfo)
                            .map(page -> page.map(Entity.class::cast))
            );

            var tabPane = new TabPane();
            addNodeToTabPane(tabPane, employeeTable, "Работники");
            return createWindow(String.format("Бригада %s", team.getName()), tabPane);
        };

        createEntityTable(
                "Бригады",
                Team.getPropertyNames(),
                Team.getSortPropertyNames(),
                ServiceFactory.getTeamService(),
                infoWindowBuilder
        );
    }

    @FXML
    void openTechInspections() {
        createEntityTable(
                "Тех. осмотры",
                TechInspection.getPropertyNames(),
                TechInspection.getSortPropertyNames(),
                ServiceFactory.getTechInspectionService(),
                null
        );
    }

    @FXML
    void openTickets() {
        createEntityTable(
                "Билеты",
                Ticket.getPropertyNames(),
                Ticket.getSortPropertyNames(),
                ServiceFactory.getTicketService(),
                null
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
    private <T extends Entity> void createEntityTable(
            String tableName,
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            Service<T> entityService,
            EntityTableController.InfoWindowBuilder<T> infoWindowBuilder
    ) {
        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
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
        controller.setInfoWindowBuilder(infoWindowBuilder);
        controller.setEntityCreator(entityService::create);
        controller.setEntityRemover(entityService::deleteById);
        controller.setEntitySource((pageInfo, filter) -> entityService.getAll(pageInfo));
        controller.setEntitySaver(entity -> entityService.save(entity.getId(), entity));
        controller.setRequestExecutor(requestExecutor);
        controller.enableContextMenu();
        controller.enableCreation();

        controller.init(
                entityPropertyNames,
                entitySortPropertyNames,
                this::setStatusBarMessage
        );
    }

    @SneakyThrows
    private <T extends Entity> Node createReadOnlyEntityTable(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            EntityTableController.EntitySource<T> entitySource
    ) {

        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        EntityTableController<T> entityTableController = tableLoader.getController();
        entityTableController.setEntitySource(entitySource);
        entityTableController.setRequestExecutor(requestExecutor);
        entityTableController.init(
                entityPropertyNames,
                entitySortPropertyNames,
                this::setStatusBarMessage
        );

        return table;
    }

    private static Stage createWindow(String windowName, Parent parentNode) {
        Stage stage = new Stage();
        stage.setTitle(windowName);
        Scene scene = new Scene(parentNode);
        stage.setScene(scene);
        return stage;
    }

    private static void addNodeToTabPane(TabPane tabPane, Node contentNode, String name) {
        Tab tab = new Tab(name);
        tab.setContent(contentNode);
        tab.setClosable(false);
        tabPane.getTabs().add(tab);
    }

}
