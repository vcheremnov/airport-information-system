package app.gui.controllers;

import app.model.Entity;
import app.services.ServiceResponse;
import app.services.filters.Filter;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;
import app.services.pagination.PageSort;
import app.utils.RequestExecutor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EntityTableController<T extends Entity> {

    interface EntitySource<E extends Entity> {
        ServiceResponse<Page<E>> getEntities(PageInfo pageInfo, Filter<E> filter) throws Exception;
    }

    interface EntityRemover<E extends Entity> {
        ServiceResponse<Void> deleteEntity(Long id) throws Exception;
    }

    interface EntitySaver<E extends Entity> {
        ServiceResponse<E> saveEntity(E entity) throws Exception;
    }

    interface EntityCreator<E extends Entity> {
        ServiceResponse<E> createEntity(E entity) throws Exception;
    }

    private EntitySource<T> entitySource;
    private EntityCreator<T> entityCreator;
    private EntitySaver<T> entitySaver;
    private EntityRemover<T> entityRemover;
    private RequestExecutor requestExecutor;

    public void setRequestExecutor(RequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void setEntitySource(EntitySource<T> entitySource) {
        this.entitySource = entitySource;
    }

    public void setEntityCreator(EntityCreator<T> entityCreator) {
        this.entityCreator = entityCreator;
    }

    public void setEntitySaver(EntitySaver<T> entitySaver) {
        this.entitySaver = entitySaver;
    }

    public void setEntityRemover(EntityRemover<T> entityRemover) {
        this.entityRemover = entityRemover;
    }

    public void enableCreation() {
        createButton.setVisible(true);
    }

    public void enableContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem infoItem = new MenuItem("Подробнее");
        MenuItem changeItem = new MenuItem("Изменить");
        MenuItem deleteItem = new MenuItem("Удалить");

        infoItem.setOnAction(event -> {
            T entity = entityTable.getSelectionModel().getSelectedItem();
            if (entity != null) {
                openInfoWindow(entity);
            }
        });

        changeItem.setOnAction(event -> {
            T entity = entityTable.getSelectionModel().getSelectedItem();
            if (entity != null) {
                openEditWindow(entity);
            }
        });

        deleteItem.setOnAction(event -> {
            T entity = entityTable.getSelectionModel().getSelectedItem();
            if (entity != null) {
                deleteEntity(entity);
            }
        });

        if (infoWindowBuilder != null) {
            contextMenu.getItems().add(infoItem);
        }

        contextMenu.getItems().addAll(changeItem, deleteItem);
        entityTable.setContextMenu(contextMenu);
    }

    interface InfoWindowBuilder<E extends Entity> {
        Stage buildInfoWindow(E entity) throws Exception;
    }

    private InfoWindowBuilder<T> infoWindowBuilder;

    public void setInfoWindowBuilder(InfoWindowBuilder<T> infoWindowBuilder) {
        this.infoWindowBuilder = infoWindowBuilder;
    }

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

    @FXML
    private Button createButton;

    private final ObservableList<T> entityObservableList = FXCollections.observableArrayList();
    private Consumer<String> statusBarMessageSetter;

    private Filter<T> filter;
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
            Consumer<String> statusBarMessageSetter
    ) {
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
    private void refreshTableContents() {
        disableComponent();
        requestExecutor
                .makeRequest(() -> entitySource.getEntities(pageInfo, filter))
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
                .setOnFailureAction(statusBarMessageSetter::accept)
                .setFinalAction(() -> Platform.runLater(this::enableComponent))
                .submit();
    }

    @FXML
    private void setAscendingSortOrder() {
        pageSort.setOrder(PageSort.Order.ASC);
    }

    @FXML
    private void setDescendingSortOrder() {
        pageSort.setOrder(PageSort.Order.DESC);
    }

    @FXML
    @SneakyThrows
    private void openCreateWindow() {
    }

    @SneakyThrows
    private void openInfoWindow(T entity) {
        if (infoWindowBuilder != null) {
            Stage infoWindow = infoWindowBuilder.buildInfoWindow(entity);
            infoWindow.show();
        }
    }

    private void openEditWindow(T entity) {

    }

    private void createEntity(T entity) {
        disableComponent();
        requestExecutor
                .makeRequest(() -> entityCreator.createEntity(entity))
                .setOnSuccessAction(createdEntity -> Platform.runLater(() -> {
                    statusBarMessageSetter.accept("Успешно добавлено");
                }))
                .setOnFailureAction(statusBarMessageSetter::accept)
                .setFinalAction(() -> Platform.runLater(this::enableComponent))
                .submit();
    }

    private void updateEntity(T entity) {
        disableComponent();
        requestExecutor
                .makeRequest(() -> entitySaver.saveEntity(entity))
                .setOnSuccessAction(editedEntity -> Platform.runLater(() -> {
                    editedEntity.calculateProperties();
                    int entityIndex = entityObservableList.indexOf(entity);
                    entityObservableList.set(entityIndex, editedEntity);
                    statusBarMessageSetter.accept("Успешно изменено");
                }))
                .setOnFailureAction(statusBarMessageSetter::accept)
                .setFinalAction(() -> Platform.runLater(this::enableComponent))
                .submit();
    }

    private void deleteEntity(T entity) {
        disableComponent();
        requestExecutor
                .makeRequest(() -> entityRemover.deleteEntity(entity.getId()))
                .setOnSuccessAction(responseBody -> Platform.runLater(() -> {
                    entityObservableList.remove(entity);
                    statusBarMessageSetter.accept("Успешно удалено");
                }))
                .setOnFailureAction(statusBarMessageSetter::accept)
                .setFinalAction(() -> Platform.runLater(this::enableComponent))
                .submit();

    }

    private void disableComponent() {
        rootVBox.setDisable(true);
    }

    private void enableComponent() {
        rootVBox.setDisable(false);
    }

}
