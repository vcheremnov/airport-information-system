package app.gui.controllers;

import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.controllers.interfaces.EntityWindowBuilder;
import app.gui.custom.ChoiceItem;
import app.model.types.FlightDelayReason;
import app.model.types.FlightType;
import app.model.types.Sex;
import app.services.*;
import app.services.pagination.PageInfo;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;
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
        AirplaneTypeService airplaneTypeService = ServiceFactory.getAirplaneTypeService();

        EntityWindowBuilder<AirplaneType> creationWindowBuilder = e -> {

            var fxmlLoader = FxmlLoaderFactory.createEntityCreationWindowLoader();
            Parent rootNode = fxmlLoader.load();
            EntityCreationController<AirplaneType> controller = fxmlLoader.getController();
            controller.init(new AirplaneType(), airplaneTypeService::create, requestExecutor);

            controller.addTextField(
                    "Название",
                    AirplaneType::setName
            );

            controller.addIntegerField(
                "Вместимость (чел.)",
                    AirplaneType::setCapacity
            );

            controller.addIntegerField(
                    "Скорость",
                    AirplaneType::setSpeed
            );

            return createStage(rootNode, "Новая модель самолёта");

        };

        createEntityTable(
                "Модели самолетов",
                AirplaneType.getPropertyNames(),
                AirplaneType.getSortPropertyNames(),
                ServiceFactory.getAirplaneTypeService(),
                creationWindowBuilder,
                null,
                null,
                null
        );
    }

    @FXML
    @SneakyThrows
    void openAirplanes() {
        AirplaneService airplaneService = ServiceFactory.getAirplaneService();
        AirplaneTypeService airplaneTypeService = ServiceFactory.getAirplaneTypeService();
        TeamService teamService = ServiceFactory.getTeamService();

        EntityWindowBuilder<Airplane> infoWindowBuilder = airplane -> {

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

        EntityWindowBuilder<Airplane> creationWindowBuilder = e -> {

            var fxmlLoader = FxmlLoaderFactory.createEntityCreationWindowLoader();
            Parent rootNode = fxmlLoader.load();
            EntityCreationController<Airplane> controller = fxmlLoader.getController();
            controller.init(new Airplane(), airplaneService::create, requestExecutor);

            ChoiceItemSupplier<Long> airplaneTypeIdSupplier = () -> {
                var page = airplaneTypeService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
                Objects.requireNonNull(page, "Не удалось загрузить список моделей самолетов");
                return page.getElementList().stream()
                        .map(m -> new ChoiceItem<>(m.getId(), m.getName()))
                        .collect(Collectors.toList());
            };


            List<Team> teamList = new ArrayList<>();

            ChoiceItemSupplier<Long> pilotTeamIdSupplier = () -> {
                var page = teamService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
                Objects.requireNonNull(page, "Не удалось загрузить список бригад");
                teamList.addAll(page.getElementList());

                return teamList.stream()
                        .filter(t -> t.getDepartment().getName().equals("Лётный отдел"))
                        .map(t -> new ChoiceItem<>(t.getId(), t.getName()))
                        .collect(Collectors.toList());
            };

            ChoiceItemSupplier<Long> techTeamIdSupplier = () -> teamList.stream()
                    .filter(t -> t.getDepartment().getName().equals("Технический отдел"))
                    .map(t -> new ChoiceItem<>(t.getId(), t.getName()))
                    .collect(Collectors.toList());

            ChoiceItemSupplier<Long> serviceTeamIdSupplier = () -> teamList.stream()
                    .filter(t -> t.getDepartment().getName().equals("Отдел обслуживания"))
                    .map(t -> new ChoiceItem<>(t.getId(), t.getName()))
                    .collect(Collectors.toList());

            controller.addChoiceBox(
                    "Модель",
                    (entity, fieldValue) -> entity.getAirplaneType().setId(fieldValue),
                    airplaneTypeIdSupplier
            );

            controller.addChoiceBox(
                    "Бригада пилотов",
                    (entity, fieldValue) -> entity.getPilotTeam().setId(fieldValue),
                    pilotTeamIdSupplier
            );

            controller.addChoiceBox(
                    "Бригада техников",
                    (entity, fieldValue) -> entity.getTechTeam().setId(fieldValue),
                    techTeamIdSupplier
            );

            controller.addChoiceBox(
                    "Бригада обслуживания",
                    (entity, fieldValue) -> entity.getServiceTeam().setId(fieldValue),
                    serviceTeamIdSupplier
            );

            return createStage(rootNode, "Новый самолёт");

        };

        createEntityTable(
                "Самолеты",
                Airplane.getPropertyNames(),
                Airplane.getSortPropertyNames(),
                ServiceFactory.getAirplaneService(),
                creationWindowBuilder,
                null,
                infoWindowBuilder,
                null
        );
    }

    @FXML
    void openChiefs() {
        ChiefService chiefService = ServiceFactory.getChiefService();

        EntityWindowBuilder<Chief> creationWindowBuilder = e -> {
            var fxmlLoader = FxmlLoaderFactory.createEntityCreationWindowLoader();
            Parent rootNode = fxmlLoader.load();
            EntityCreationController<Chief> controller = fxmlLoader.getController();
            controller.init(new Chief(), chiefService::create, requestExecutor);

            controller.addTextField("ФИО начальника", Chief::setName);
            controller.addChoiceBox("Пол", Chief::setSex,
                    () -> Arrays.stream(Sex.values())
                            .map(s -> new ChoiceItem<>(s, Sex.toLocalizedString(s)))
                            .collect(Collectors.toList())
            );
            controller.addDateTimeField("Дата рождения", Chief::setBirthDate);

            return createStage(rootNode, "Новый начальник");
        };

        createEntityTable(
                "Начальники",
                Chief.getPropertyNames(),
                Chief.getSortPropertyNames(),
                ServiceFactory.getChiefService(),
                creationWindowBuilder,
                null,
                null,
                null
        );
    }

    @FXML
    void openCities() {
        TicketService ticketService = ServiceFactory.getTicketService();
        CityService cityService = ServiceFactory.getCityService();

        EntityWindowBuilder<City> infoWindowBuilder = city -> {
            FXMLLoader entityInfoLoader = FxmlLoaderFactory.createEntityInfoLoader();
            Parent entityInfoRoot = entityInfoLoader.load();
            EntityInfoController controller = entityInfoLoader.getController();

            requestExecutor
                    .makeRequest(() -> ticketService.getAverageSoldByCity(city.getId()))
                    .setOnSuccessAction(averageSold -> Platform.runLater(() -> {
                        controller.addInfoLine(String.format(
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

        EntityWindowBuilder<City> creationWindowBuilder = e -> {
            var fxmlLoader = FxmlLoaderFactory.createEntityCreationWindowLoader();
            Parent rootNode = fxmlLoader.load();
            EntityCreationController<City> controller = fxmlLoader.getController();
            controller.init(new City(), cityService::create, requestExecutor);

            controller.addTextField(
                    "Название",
                     City::setName
            );

            controller.addIntegerField(
                    "Расстояние (км)",
                    City::setDistance
            );

            return createStage(rootNode, "Новый город");
        };

        createEntityTable(
                "Города",
                City.getPropertyNames(),
                City.getSortPropertyNames(),
                ServiceFactory.getCityService(),
                creationWindowBuilder,
                null,
                infoWindowBuilder,
                null
        );
    }

    @FXML
    @SneakyThrows
    void openDepartments() {
        DepartmentService departmentService = ServiceFactory.getDepartmentService();
        ChiefService chiefService = ServiceFactory.getChiefService();

        EntityWindowBuilder<Department> infoWindowBuilder = department -> {
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

        EntityWindowBuilder<Department> creationWindowBuilder = e -> {
            var fxmlLoader = FxmlLoaderFactory.createEntityCreationWindowLoader();
            Parent rootNode = fxmlLoader.load();
            EntityCreationController<Department> controller = fxmlLoader.getController();

            controller.init(new Department(), departmentService::create, requestExecutor);

            controller.addTextField(
                    "Название",
                    Department::setName
            );

            ChoiceItemSupplier<Long> chiefIdSupplier = () -> {
                var page = chiefService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
                Objects.requireNonNull(page, "Не удалось загрузить список начальников");

                return page.getElementList().stream()
                        .map(c -> new ChoiceItem<>(c.getId(), c.getName()))
                        .collect(Collectors.toList());
            };

            controller.addChoiceBox(
                    "Начальник",
                    (entity, fieldValue) -> entity.getChief().setId(fieldValue),
                    chiefIdSupplier
            );

            return createStage(rootNode, "Новый отдел");
        };

        createEntityTable(
                "Отделы",
                Department.getPropertyNames(),
                Department.getSortPropertyNames(),
                ServiceFactory.getDepartmentService(),
                creationWindowBuilder,
                null,
                infoWindowBuilder,
                null
        );
    }

    @FXML
    @SneakyThrows
    void openEmployees() {
        EmployeeService employeeService = ServiceFactory.getEmployeeService();
        TeamService teamService = ServiceFactory.getTeamService();

        EntityWindowBuilder<Employee> infoWindowBuilder = employee -> {
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

        EntityWindowBuilder<Employee> creationWindowBuilder = e -> {
            var fxmlLoader = FxmlLoaderFactory.createEntityCreationWindowLoader();
            Parent rootNode = fxmlLoader.load();
            EntityCreationController<Employee> controller = fxmlLoader.getController();

            controller.init(new Employee(), employeeService::create, requestExecutor);

            controller.addTextField(
                    "ФИО сотрудника",
                    Employee::setName
            );

            controller.addChoiceBox("Пол", Employee::setSex,
                    () -> Arrays.stream(Sex.values())
                            .map(s -> new ChoiceItem<>(s, Sex.toLocalizedString(s)))
                            .collect(Collectors.toList())
            );

            controller.addDateTimeField(
                    "Дата рождения",
                    Employee::setBirthDate
            );

            ChoiceItemSupplier<Long> teamIdSupplier = () -> {
                var page = teamService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
                Objects.requireNonNull(page, "Не удалось загрузить список бригад");

                return page.getElementList().stream()
                        .map(t -> new ChoiceItem<>(t.getId(), t.getName()))
                        .collect(Collectors.toList());
            };

            controller.addChoiceBox(
                    "Бригада",
                    (entity, fieldValue) -> entity.getTeam().setId(fieldValue),
                    teamIdSupplier
            );

            controller.addIntegerField(
                    "Зарплата",
                    Employee::setSalary
            );

            return createStage(rootNode, "Новый сотрудник");
        };

        createEntityTable(
                "Сотрудники",
                Employee.getPropertyNames(),
                Employee.getSortPropertyNames(),
                ServiceFactory.getEmployeeService(),
                creationWindowBuilder,
                null,
                infoWindowBuilder,
                null
        );
    }

    @FXML
    void openFlights() {
        FlightService flightService = ServiceFactory.getFlightService();
        AirplaneService airplaneService = ServiceFactory.getAirplaneService();
        CityService cityService = ServiceFactory.getCityService();

        EntityWindowBuilder<Flight> infoWindowBuilder = flight -> {
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

            var entityInfoLoader = FxmlLoaderFactory.createEntityInfoLoader();
            Node entityInfoList = entityInfoLoader.load();
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

        EntityWindowBuilder<Flight> creationWindowBuilder = e -> {
            var fxmlLoader = FxmlLoaderFactory.createEntityCreationWindowLoader();
            Parent rootNode = fxmlLoader.load();
            EntityCreationController<Flight> controller = fxmlLoader.getController();

            controller.init(new Flight(), flightService::create, requestExecutor);

            ChoiceItemSupplier<Long> airplaneIdSupplier = () -> {
                var page = airplaneService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
                Objects.requireNonNull(page, "Не удалось загрузить список самолетов");

                return page.getElementList().stream()
                        .map(a -> new ChoiceItem<>(a.getId(),
                                String.format("№%d (%s)", a.getId(), a.getAirplaneType().getName())
                        )).collect(Collectors.toList());
            };

            ChoiceItemSupplier<Long> cityIdSupplier = () -> {
                var page = cityService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
                Objects.requireNonNull(page, "Не удалось загрузить список бригад");

                return page.getElementList().stream()
                        .map(c -> new ChoiceItem<>(c.getId(), c.getName()))
                        .collect(Collectors.toList());
            };

            ChoiceItemSupplier<FlightType> flightTypeSupplier = () ->
                    Arrays.stream(FlightType.values())
                    .map(t -> new ChoiceItem<>(t, FlightType.toLocalizedString(t)))
                    .collect(Collectors.toList());

            controller.addChoiceBox(
                    "Самолёт",
                    Flight::setAirplaneId,
                    airplaneIdSupplier
            );

            controller.addChoiceBox(
                    "Город",
                    (entity, fieldValue) -> entity.getCity().setId(fieldValue),
                    cityIdSupplier
            );

            controller.addChoiceBox(
                    "Тип рейса",
                    Flight::setFlightType,
                    flightTypeSupplier
            );

            controller.addDateTimeField(
                    "Время",
                    Flight::setFlightTime
            );

            return createStage(rootNode, "Новый рейс");
        };

        createEntityTable(
                "Рейсы",
                Flight.getPropertyNames(),
                Flight.getSortPropertyNames(),
                ServiceFactory.getFlightService(),
                creationWindowBuilder,
                null,
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
                null,
                null,
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
                null,
                null,
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
                null,
                null,
                null,
                null
        );
    }

    @FXML
    @SneakyThrows
    void openTeams() {
        TeamService teamService = ServiceFactory.getTeamService();
        DepartmentService departmentService = ServiceFactory.getDepartmentService();

        EntityWindowBuilder<Team> infoWindowBuilder = team -> {
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

        EntityWindowBuilder<Team> creationWindowBuilder = e -> {
            var fxmlLoader = FxmlLoaderFactory.createEntityCreationWindowLoader();
            Parent rootNode = fxmlLoader.load();
            EntityCreationController<Team> controller = fxmlLoader.getController();

            controller.init(new Team(), teamService::create,requestExecutor);

            controller.addTextField(
                    "Название",
                    Team::setName
            );

            ChoiceItemSupplier<Long> departmentIdSupplier = () -> {
                var page = departmentService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
                Objects.requireNonNull(page, "Не удалось загрузить список отделов");

                return page.getElementList().stream()
                        .map(d -> new ChoiceItem<>(d.getId(), d.getName()))
                        .collect(Collectors.toList());
            };

            controller.addChoiceBox(
                    "Отдел",
                    (entity, fieldValue) -> entity.getDepartment().setId(fieldValue),
                    departmentIdSupplier
            );

            return createStage(rootNode, "Новая бригада");
        };

        createEntityTable(
                "Бригады",
                Team.getPropertyNames(),
                Team.getSortPropertyNames(),
                ServiceFactory.getTeamService(),
                creationWindowBuilder,
                null,
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
                null,
                null,
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
                null,
                null,
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
            EntityWindowBuilder<T> creationWindowBuilder,
            EntityWindowBuilder<T> editWindowBuilder,
            EntityWindowBuilder<T> infoWindowBuilder,
            Map<String, EntityWindowBuilder<T>> contextWindowBuilders
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
        controller.setCreationWindowBuilder(creationWindowBuilder);
        controller.setInfoWindowBuilder(infoWindowBuilder);
        controller.setEditWindowBuilder(editWindowBuilder);

        controller.setEntityRemover(entityService::deleteById);
        controller.setEntitySource((pageInfo, filter) -> entityService.getAll(pageInfo));
        controller.setEntitySaver(entity -> entityService.save(entity.getId(), entity));
        controller.setRequestExecutor(requestExecutor);

        if (contextWindowBuilders != null) {
            contextWindowBuilders.forEach(controller::addContextWindowBuilder);
        }
        controller.enableContextMenu();

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

    private static Stage createStage(Parent rootNode, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.sizeToScene();
        return stage;
    }

}
