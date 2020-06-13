package app.gui.controllers;

import app.gui.controllers.interfaces.ContextWindowBuilder;
import app.gui.forms.EntityInputFormBuilder;
import app.gui.forms.impl.*;
import app.model.types.FlightDelayReason;
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
import javafx.scene.control.*;
import lombok.SneakyThrows;

import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;

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
                new AirplaneTypeInputFormBuilder(requestExecutor),
                null,
                null
        );
    }

    @FXML
    @SneakyThrows
    void openAirplanes() {
        AirplaneService airplaneService = ServiceFactory.getAirplaneService();
        TechInspectionService techInspectionService = ServiceFactory.getTechInspectionService();
        RepairService repairService = ServiceFactory.getRepairService();

        ContextWindowBuilder<Airplane> infoWindowBuilder = airplane -> {

            var techInspectionPropertyNames = new LinkedHashMap<>(TechInspection.getPropertyNames());
            techInspectionPropertyNames.remove("airplaneIdProperty");
            techInspectionPropertyNames.remove("airplaneTypeNameProperty");

            var techInspectionSortPropertyNames = new LinkedHashMap<>(TechInspection.getSortPropertyNames());
            techInspectionSortPropertyNames.remove("airplaneId");
            techInspectionSortPropertyNames.remove("airplaneAirplaneTypeName");

            Node techInspectionsTable = createInfoWindowEntityTable(
                    techInspectionPropertyNames,
                    techInspectionSortPropertyNames,
                    (pageInfo, filter) -> airplaneService.getTechInspections(airplane.getId(), pageInfo),
                    techInspectionService::deleteById,
                    new TechInspectionInputFormBuilder(requestExecutor),
                    () -> {
                        TechInspection techInspection = new TechInspection();
                        techInspection.getAirplane().setId(airplane.getId());
                        return techInspection;
                    }
            );

            var repairPropertyNames = new LinkedHashMap<>(Repair.getPropertyNames());
            repairPropertyNames.remove("airplaneIdProperty");
            repairPropertyNames.remove("airplaneTypeNameProperty");

            var repairSortPropertyNames = new LinkedHashMap<>(Repair.getSortPropertyNames());
            repairSortPropertyNames.remove("airplaneId");
            repairSortPropertyNames.remove("airplaneAirplaneTypeName");

            Node repairTable = createInfoWindowEntityTable(
                    repairPropertyNames,
                    repairSortPropertyNames,
                    (pageInfo, filter) -> airplaneService.getRepairs(airplane.getId(), pageInfo),
                    repairService::deleteById,
                    new RepairInputFormBuilder(requestExecutor),
                    () -> {
                        Repair repair = new Repair();
                        repair.getAirplane().setId(airplane.getId());
                        return repair;
                    }
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
                new AirplaneInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                null
        );
    }

    @FXML
    void openChiefs() {
        createEntityTable(
                "Начальники",
                Chief.getPropertyNames(),
                Chief.getSortPropertyNames(),
                ServiceFactory.getChiefService(),
                new ChiefInputFormBuilder(requestExecutor),
                null,
                null
        );
    }

    @FXML
    void openCities() {
        TicketService ticketService = ServiceFactory.getTicketService();

        ContextWindowBuilder<City> infoWindowBuilder = city -> {
            FXMLLoader entityInfoLoader = FxmlLoaderFactory.createEntityInfoLoader();
            Parent entityInfoRoot = null;
            try {
                entityInfoRoot = entityInfoLoader.load();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            EntityInfoController controller = entityInfoLoader.getController();

            requestExecutor
                    .makeRequest(() -> ticketService.getAverageSoldByCity(city.getId()))
                    .setOnSuccessAction(averageSold -> Platform.runLater(() -> {
                        controller.addInfoLine(String.format(
                                "Среднее число проданных билетов на рейсы до города: %.2f", averageSold
                        ));
                    }))
                    .setOnFailureAction(errorMessage -> AlertDialogFactory.showErrorAlertDialog(
                            "Не удалось загрузить информацию о городе",
                            errorMessage
                    ))
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
                new CityInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                null
        );
    }

    @FXML
    @SneakyThrows
    void openDepartments() {
        DepartmentService departmentService = ServiceFactory.getDepartmentService();
        TeamService teamService = ServiceFactory.getTeamService();

        ContextWindowBuilder<Department> infoWindowBuilder = department -> {
            var teamPropertyNames = new LinkedHashMap<>(Team.getPropertyNames());
            teamPropertyNames.remove("departmentNameProperty");
            var teamSortPropertyNames = new LinkedHashMap<>(Team.getSortPropertyNames());
            teamSortPropertyNames.remove("departmentName");

            Node teamsTable = createInfoWindowEntityTable(
                    teamPropertyNames,
                    teamSortPropertyNames,
                    (pageInfo, filter) -> departmentService.getTeams(department.getId(), pageInfo),
                    teamService::deleteById,
                    new TeamInputFormBuilder(requestExecutor),
                    () -> {
                        Team team = new Team();
                        team.getDepartment().setId(department.getId());
                        return team;
                    }
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
                new DepartmentInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                null
        );
    }

    @FXML
    @SneakyThrows
    void openEmployees() {
        EmployeeService employeeService = ServiceFactory.getEmployeeService();
        var medicalExaminationService = ServiceFactory.getMedicalExaminationService();

        ContextWindowBuilder<Employee> infoWindowBuilder = employee -> {
            var medExamPropertyNames = new LinkedHashMap<>(MedicalExamination.getPropertyNames());
            medExamPropertyNames.remove("employeeNameProperty");
            var medExamSortPropertyNames = new LinkedHashMap<>(MedicalExamination.getSortPropertyNames());
            medExamSortPropertyNames.remove("employeeName");

            Node medExamTable = createInfoWindowEntityTable(
                    medExamPropertyNames,
                    medExamSortPropertyNames,
                    (pageInfo, filter) -> employeeService.getMedicalExaminations(employee.getId(), pageInfo),
                    medicalExaminationService::deleteById,
                    new MedicalExaminationInputFormBuilder(requestExecutor),
                    () -> {
                        var medicalExamination = new MedicalExamination();
                        medicalExamination.getEmployee().setId(employee.getId());
                        return medicalExamination;
                    }
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
                new EmployeeInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                null
        );
    }

    @FXML
    void openFlights() {
        FlightService flightService = ServiceFactory.getFlightService();
        TicketService ticketService = ServiceFactory.getTicketService();

        ContextWindowBuilder<Flight> infoWindowBuilder = flight -> {
            var ticketPropertyNames = new LinkedHashMap<>(Ticket.getPropertyNames());
            ticketPropertyNames.remove("flightId");
            ticketPropertyNames.remove("priceProperty");
            var ticketSortPropertyNames = new LinkedHashMap<>(Ticket.getSortPropertyNames());
            ticketSortPropertyNames.remove("flightId");

            Node ticketsTable = createInfoWindowEntityTable(
                    ticketPropertyNames,
                    ticketSortPropertyNames,
                    (pageInfo, filter) -> flightService.getTickets(flight.getId(), pageInfo),
                    ticketService::deleteById,
                    new TicketInputFormBuilder(requestExecutor),
                    () -> {
                        Ticket ticket = new Ticket();
                        ticket.setFlightId(flight.getId());
                        return ticket;
                    }
            );

            var entityInfoLoader = FxmlLoaderFactory.createEntityInfoLoader();
            Parent entityInfoList = null;
            try {
                entityInfoList = entityInfoLoader.load();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            EntityInfoController entityInfoController = entityInfoLoader.getController();
            entityInfoController.addInfoLine(
                    String.format("Цена билета: %.2f р.", flight.getTicketPrice())
            );
            entityInfoController.addInfoLine(
                    String.format("Билетов продано: %d", flight.getTicketsSold())
            );
            entityInfoController.addInfoLine(
                    String.format("Билетов забронировано: %d", flight.getTicketsBooked())
            );
            entityInfoController.addInfoLine(
                    String.format("Билетов возвращено: %d", flight.getTicketsReturned())
            );
            if (flight.getFlightDelay() != null) {
                entityInfoController.addInfoLine(
                    String.format(
                            "Причина задержки рейса: %s",
                            FlightDelayReason.toLocalizedString(
                                    flight.getFlightDelay().getDelayReason()
                            )
                    )
                );
            }

            return EntityInfoWindowBuilder
                    .newInfoWindow(String.format("Рейс №%d", flight.getId()))
                    .addTab(entityInfoList, "Доп. информация")
                    .addTab(ticketsTable, "Билеты")
                    .build();
        };

        createEntityTable(
                "Рейсы",
                Flight.getPropertyNames(),
                Flight.getSortPropertyNames(),
                ServiceFactory.getFlightService(),
                new FlightInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                null
        );
    }

    @FXML
    void openMedExams() {
        createEntityTable(
                "Мед. осмотры",
                MedicalExamination.getPropertyNames(),
                MedicalExamination.getSortPropertyNames(),
                ServiceFactory.getMedicalExaminationService(),
                new MedicalExaminationInputFormBuilder(requestExecutor),
                null,
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
                new PassengerInputFormBuilder(requestExecutor),
                null,
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
                new RepairInputFormBuilder(requestExecutor),
                null,
                null
        );
    }

    @FXML
    @SneakyThrows
    void openTeams() {
        TeamService teamService = ServiceFactory.getTeamService();
        EmployeeService employeeService = ServiceFactory.getEmployeeService();

        ContextWindowBuilder<Team> infoWindowBuilder = team -> {
            var employeePropertyNames = new LinkedHashMap<>(Employee.getPropertyNames());
            employeePropertyNames.remove("departmentNameProperty");
            employeePropertyNames.remove("teamNameProperty");
            var employeeSortPropertyNames = new LinkedHashMap<>(Employee.getSortPropertyNames());
            employeeSortPropertyNames.remove("teamDepartmentName");
            employeeSortPropertyNames.remove("teamName");

            Node employeeTable = createInfoWindowEntityTable(
                    employeePropertyNames,
                    employeeSortPropertyNames,
                    (pageInfo, filter) -> teamService.getEmployees(team.getId(), pageInfo),
                    employeeService::deleteById,
                    new EmployeeInputFormBuilder(requestExecutor),
                    () -> {
                        Employee employee = new Employee();
                        employee.getTeam().setId(team.getId());
                        return employee;
                    }
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
                new TeamInputFormBuilder(requestExecutor),
                infoWindowBuilder,
                null
        );
    }

    @FXML
    void openTechInspections() {
        createEntityTable(
                "Тех. осмотры",
                TechInspection.getPropertyNames(),
                TechInspection.getSortPropertyNames(),
                ServiceFactory.getTechInspectionService(),
                new TechInspectionInputFormBuilder(requestExecutor),
                null,
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
                new TicketInputFormBuilder(requestExecutor),
                null,
                null
        );
    }

    private void setStatusBarMessage(String message) {
        Platform.runLater(() -> {
            String messageTime = LocalDateFormatter.getFormattedDateTime(Instant.now().toEpochMilli());
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
            EntityInputFormBuilder<T> inputFormBuilder,
            ContextWindowBuilder<T> infoWindowBuilder,
            Map<String, ContextWindowBuilder<T>> contextWindowBuilders
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

        controller.setEntityRemover(entityService::deleteById);
        controller.setEntitySource((pageInfo, filter) -> entityService.getAll(pageInfo));
        controller.setRequestExecutor(requestExecutor);

        if (contextWindowBuilders != null) {
            contextWindowBuilders.forEach(controller::addContextWindowBuilder);
        }

        controller.init(
                entityPropertyNames,
                entitySortPropertyNames,
                inputFormBuilder,
                null,
                false,
                this::setStatusBarMessage
        );
    }

    @SneakyThrows
    private <T extends Entity> Node createInfoWindowEntityTable(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            EntityTableController.EntitySource<T> entitySource,
            EntityTableController.EntityRemover<T> entityRemover,
            EntityInputFormBuilder<T> inputFormBuilder,
            Supplier<T> newEntitySupplier
    ) {

        FXMLLoader tableLoader = FxmlLoaderFactory.createEntityTableLoader();
        Node table = tableLoader.load();

        EntityTableController<T> entityTableController = tableLoader.getController();
        entityTableController.setEntitySource(entitySource);
        entityTableController.setEntityRemover(entityRemover);
        entityTableController.setRequestExecutor(requestExecutor);
        entityTableController.init(
                entityPropertyNames,
                entitySortPropertyNames,
                inputFormBuilder,
                newEntitySupplier,
                true,
                this::setStatusBarMessage
        );

        return table;
    }

}
