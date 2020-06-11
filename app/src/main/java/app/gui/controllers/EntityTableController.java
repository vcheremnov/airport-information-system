package app.gui.controllers;

import app.gui.controllers.interfaces.EntityWindowBuilder;
import app.gui.custom.ChoiceItem;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
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
    private EntitySaver<T> entitySaver;
    private EntityRemover<T> entityRemover;
    private RequestExecutor requestExecutor;
    private EntityWindowBuilder<T> infoWindowBuilder;
    private EntityWindowBuilder<T> creationWindowBuilder;
    private EntityWindowBuilder<T> editWindowBuilder;
    private Map<String, EntityWindowBuilder<T>> contextWindowBuilders = new LinkedHashMap<>();

    public void setInfoWindowBuilder(EntityWindowBuilder<T> infoWindowBuilder) {
        this.infoWindowBuilder = infoWindowBuilder;
    }

    public void setCreationWindowBuilder(EntityWindowBuilder<T> creationWindowBuilder) {
        this.creationWindowBuilder = creationWindowBuilder;
    }

    public void setEditWindowBuilder(EntityWindowBuilder<T> editWindowBuilder) {
        this.editWindowBuilder = editWindowBuilder;
    }

    public void setRequestExecutor(RequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void setEntitySource(EntitySource<T> entitySource) {
        this.entitySource = entitySource;
    }

    public void setEntitySaver(EntitySaver<T> entitySaver) {
        this.entitySaver = entitySaver;
    }

    public void setEntityRemover(EntityRemover<T> entityRemover) {
        this.entityRemover = entityRemover;
    }

    public void addContextWindowBuilder(
            String contextMenuItemName,
            EntityWindowBuilder<T> contextWindowBuilder
    ) {
        contextWindowBuilders.put(contextMenuItemName, contextWindowBuilder);
    }

    public void enableContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        for (var contextMenuItemName: contextWindowBuilders.keySet()) {
            var contextWindowBuilder = contextWindowBuilders.get(contextMenuItemName);
            MenuItem menuItem = new MenuItem(contextMenuItemName);
            menuItem.setOnAction(event -> {
                T entity = entityTable.getSelectionModel().getSelectedItem();
                if (entity != null) {
                    openContextWindow(entity, contextWindowBuilder);
                }
            });

            contextMenu.getItems().add(menuItem);
        }

        MenuItem infoItem = new MenuItem("Подробнее");
        MenuItem changeItem = new MenuItem("Изменить");
        MenuItem deleteItem = new MenuItem("Удалить");

        infoItem.setOnAction(event -> {
            T entity = entityTable.getSelectionModel().getSelectedItem();
            if (entity != null) {
                openContextWindow(entity, infoWindowBuilder);
            }
        });

        changeItem.setOnAction(event -> {
            T entity = entityTable.getSelectionModel().getSelectedItem();
            if (entity != null) {
                openContextWindow(entity, editWindowBuilder);
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

    @FXML
    private VBox rootVBox;

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<T> entityTable;

    @FXML
    private ChoiceBox<ChoiceItem<String>> sortChoiceBox;

    @FXML
    private VBox filteringVBox;

    @FXML
    private Button createButton;

    private final ObservableList<T> entityObservableList = FXCollections.observableArrayList();
    private Consumer<String> statusBarMessageSetter;

    private Filter<T> filter;
    private PageInfo pageInfo;
    private PageSort pageSort;

    public void init(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            Consumer<String> statusBarMessageSetter
    ) {
        this.statusBarMessageSetter = statusBarMessageSetter;

        pageSort = new PageSort();
        pageInfo = new PageInfo(0L, 23L, pageSort);

        pagination.pageCountProperty().setValue(1);
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            int newPageNumber = (int) newValue;
            pageInfo.setPageNumber((long) newPageNumber);
            refreshTableContents();
        });

        entityTable.placeholderProperty().setValue(new Label());

        List<ChoiceItem<String>> sortFieldList = entitySortPropertyNames
                .entrySet()
                .stream()
                .map(e -> new ChoiceItem<>(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        ChoiceItem<String> defaultSortField = new ChoiceItem<>(null, "Не указано");
        sortFieldList.add(defaultSortField);
        sortChoiceBox.setValue(defaultSortField);
        sortChoiceBox.getItems().addAll(sortFieldList);
        sortChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            pageSort.removeAllFields();
            if (newValue.getItem() != null) {
                pageSort.addField(newValue.getItem());
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


        if (creationWindowBuilder == null) {
            createButton.setVisible(false);
        }

        refreshTableContents();
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
    private void openCreateWindow() {
        openContextWindow(null, creationWindowBuilder);
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

    private void openContextWindow(T entity, EntityWindowBuilder<T> windowBuilder) {
        try {
            if (windowBuilder != null) {
                Stage contextWindow = windowBuilder.buildWindow(entity);
                contextWindow.show();
            }
        } catch (Exception e) {
            // TODO: show pop-up window with an error message
            e.printStackTrace();
        }
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
                // TODO: всплывающее окно при ошибке
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
