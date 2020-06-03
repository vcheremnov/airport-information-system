package app.gui.controllers;

import app.model.Entity;
import app.model.LocalDateFormatter;
import app.services.Service;
import app.services.pagination.PageInfo;
import app.services.pagination.PageSort;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EntityTableController<T extends Entity> {

    @FXML
    private VBox rootVBox;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<T> entityTable;

    @FXML
    private VBox filteringVBox;

    @FXML
    private Button refreshButton;

    private final ObservableList<T> entityObservableList = FXCollections.observableArrayList();

    private RequestExecutor requestExecutor;
    private Service<T> entityService;
    private PageInfo pageInfo;
    private PageSort pageSort;
    private Consumer<String> statusBarMessageSetter;

    public void init(
            Map<String, String> entityPropertyNames,
            RequestExecutor requestExecutor,
            Service<T> entityService,
            Consumer<String> statusBarMessageSetter
    ) {
        this.entityService = entityService;
        this.requestExecutor = requestExecutor;
        this.statusBarMessageSetter = statusBarMessageSetter;

        pageSort = new PageSort();
        pageInfo = new PageInfo(0L, 23L, pageSort);

        Label tablePlaceholder = new Label("Нажмите \"Обновить\" для отображения данных");
        entityTable.placeholderProperty().setValue(tablePlaceholder);
        pagination.setDisable(true);

        List<TableColumn<T, String>> columns = entityPropertyNames
                .entrySet()
                .stream()
                .map(e -> {
                    TableColumn<T, String> tableColumn = new TableColumn<>(e.getValue());
                    tableColumn.setCellValueFactory(new PropertyValueFactory<>(e.getKey()));
                    tableColumn.setSortable(false);
                    tableColumn.setEditable(false);
                    return tableColumn;
                }).collect(Collectors.toList());

        entityTable.getColumns().addAll(columns);
        entityTable.setItems(entityObservableList);
    }

    @FXML
    void refreshTableContents(ActionEvent event) {
        setDisable(true);
        requestExecutor
                .makeRequest(() -> entityService.getAll(pageInfo))
                .setOnSuccessAction(page -> Platform.runLater(() -> {
                    var entities = page.getElementList();
                    entities.forEach(Entity::calculateProperties);

                    entityObservableList.clear();
                    entityObservableList.addAll(entities);
                    statusBarMessageSetter.accept("Данные успешно загружены");
                }))
                .setOnFailureAction(errorMsg -> statusBarMessageSetter.accept(errorMsg))
                .setFinalAction(() -> Platform.runLater(() -> setDisable(false)))
                .submit();
    }

    private void setDisable(boolean value) {
        rootVBox.setDisable(value);
    }

}
