package app.gui.controllers;

import app.model.Entity;
import app.services.Service;
import app.services.pagination.PageInfo;
import app.services.pagination.PageSort;
import app.utils.RequestExecutor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EntityTableController<T extends Entity> {

    @FXML
    private VBox rootVBox;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<T> entityTable;

    @FXML
    private ChoiceBox<SortField> sortChoiceBox;

    @FXML
    private VBox filteringVBox;

    private final ObservableList<T> entityObservableList = FXCollections.observableArrayList();
    private RequestExecutor requestExecutor;
    private Service<T> entityService;
    private Consumer<String> statusBarMessageSetter;
    private PageInfo pageInfo;
    private PageSort pageSort;

    @AllArgsConstructor
    private static class SortField {
        private final String key;
        private final String value;

        @Override
        public String toString() {
            return value;
        }
    }

    public void init(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            RequestExecutor requestExecutor,
            Service<T> entityService,
            Consumer<String> statusBarMessageSetter
    ) {
        this.entityService = entityService;
        this.requestExecutor = requestExecutor;
        this.statusBarMessageSetter = statusBarMessageSetter;

        pageSort = new PageSort();
        pageInfo = new PageInfo(0L, 23L, pageSort);

        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int newPageNumber = (int) newValue;
            pageInfo.setPageNumber((long) newPageNumber);
            refreshTableContents();
        });

        Label tablePlaceholder = new Label("Нажмите \"Обновить\" для отображения данных");
        entityTable.placeholderProperty().setValue(tablePlaceholder);

        List<SortField> sortFieldList = entitySortPropertyNames
                .entrySet()
                .stream()
                .map(e -> new SortField(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        SortField defaultSortField = new SortField(null, "Не указано");
        sortFieldList.add(defaultSortField);
        sortChoiceBox.setValue(defaultSortField);
        sortChoiceBox.getItems().addAll(sortFieldList);
        sortChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            pageSort.removeAllFields();
            if (newValue.key != null) {
                pageSort.addField(newValue.key);
            }
        });

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

        refreshTableContents();
    }

    @FXML
    void refreshTableContents() {
        disableComponent();
        requestExecutor
                .makeRequest(() -> entityService.getAll(pageInfo))
                .setOnSuccessAction(page -> {
                    var entities = page.getElementList();
                    entities.forEach(Entity::calculateProperties);

                    Platform.runLater(() -> {
                        entityObservableList.clear();
                        entityObservableList.addAll(entities);
                        statusBarMessageSetter.accept("Данные успешно загружены");
                        pagination.pageCountProperty().setValue(page.getTotalPages());
                    });
                })
                .setOnFailureAction(errorMsg -> statusBarMessageSetter.accept(errorMsg))
                .setFinalAction(() -> Platform.runLater(this::enableComponent))
                .submit();
    }

    @FXML
    void setAscendingSortOrder() {
        pageSort.setOrder(PageSort.Order.ASC);
    }

    @FXML
    void setDescendingSortOrder() {
        pageSort.setOrder(PageSort.Order.DESC);
    }

    private void disableComponent() {
        rootVBox.setDisable(true);
    }

    private void enableComponent() {
        rootVBox.setDisable(false);
    }

}
