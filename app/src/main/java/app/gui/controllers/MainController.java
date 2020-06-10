package app.gui.controllers;

import app.gui.custom.ChoiceBoxItem;
import app.gui.custom.DateTimePicker;
import app.model.types.Sex;
import app.services.*;
import app.utils.LocalDateFormatter;
import app.utils.ServiceFactory;
import app.model.*;
import app.utils.RequestExecutor;
import com.google.gson.GsonBuilder;
import com.sun.javafx.scene.control.IntegerField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import lombok.SneakyThrows;

import java.time.Instant;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

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

            return EntityInfoWindowBuilder
                    .newInfoWindow(String.format("Самолёт №%d", airplane.getId()))
                    .addTab(techInspectionsTable, "Тех. осмотры")
                    .addTab(repairTable, "Ремонты")
                    .build();
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
        TicketService ticketService = ServiceFactory.getTicketService();
        EntityTableController.InfoWindowBuilder<City> infoWindowBuilder = city -> {
            FXMLLoader entityInfoLoader = FxmlLoaderFactory.createEntityInfoLoader();
            Parent entityInfoRoot = entityInfoLoader.load();
            EntityInfoController controller = entityInfoLoader.getController();

            requestExecutor
                    .makeRequest(() -> ticketService.getAverageSoldByCity(city.getId()))
                    .setOnSuccessAction(averageSold -> Platform.runLater(() -> {
                        controller.addLine(String.format(
                                "Среднее число проданных билетов на рейс: %.2f", averageSold
                        ));
                    }))
                    .setOnFailureAction(this::setStatusBarMessage)
                    .submit();

            return EntityInfoWindowBuilder
                    .newInfoWindow(city.getName())
                    .addTab(entityInfoRoot, "Доп. информация")
                    .build();
        };

        createEntityTable(
                "Города",
                City.getPropertyNames(),
                City.getSortPropertyNames(),
                ServiceFactory.getCityService(),
                infoWindowBuilder
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


            return EntityInfoWindowBuilder
                    .newInfoWindow(department.getName())
                    .addTab(teamsTable, "Бригады")
                    .build();
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


            return EntityInfoWindowBuilder
                    .newInfoWindow(String.format("Работник %s", employee.getName()))
                    .addTab(medExamTable, "Мед. осмотры")
                    .build();
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

            return EntityInfoWindowBuilder
                    .newInfoWindow(String.format("Рейс №%d", flight.getId()))
                    .addTab(ticketsTable, "Билеты")
                    .build();
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
//        var fxmlLoader = FxmlLoaderFactory.createEntityCreationWindowLoader();
//        Parent rootNode = fxmlLoader.load();
//        EntityCreationController<Passenger> controller = fxmlLoader.getController();
//
//        Passenger passenger = new Passenger();
//        PassengerService passengerService = ServiceFactory.getPassengerService();
//
//        controller.addTextField("ФИО пассажира", Passenger::setName);
//        controller.addChoiceBox("Пол", Passenger::setSex,
//                () -> Arrays.stream(Sex.values())
//                        .map(s -> new ChoiceBoxItem<>(s, Sex.toLocalizedString(s)))
//                        .collect(Collectors.toList())
//        );
//        controller.addDateTimeField("Дата рождения", Passenger::setBirthDate);
//
//        controller.init(
//                passenger,
//                passengerService::create,
//                requestExecutor
//        );
//
//        return EntityInfoWindowBuilder
//                .newInfoWindow("TEST TEST TEST")
//                .addTab(rootNode, "TEST")
//                .build();


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

            return EntityInfoWindowBuilder
                    .newInfoWindow(String.format("Бригада %s", team.getName()))
                    .addTab(employeeTable, "Работники")
                    .build();
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

}
