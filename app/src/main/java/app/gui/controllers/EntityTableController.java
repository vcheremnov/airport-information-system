package app.gui.controllers;

import app.gui.controllers.interfaces.ContextWindowBuilder;
import app.gui.controllers.interfaces.SuccessAction;
import app.gui.custom.ChoiceItem;
import app.gui.forms.EntityInputFormBuilder;
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class EntityTableController<T extends Entity> {

    public interface EntitySource<E extends Entity> {
        ServiceResponse<Page<E>> getEntities(PageInfo pageInfo, Filter<E> filter) throws Exception;
    }

    public interface EntityRemover<E extends Entity> {
        ServiceResponse<Void> deleteEntity(Long id) throws Exception;
    }

    private EntitySource<T> entitySource;
    private EntityRemover<T> entityRemover;
    private RequestExecutor requestExecutor;
    private ContextWindowBuilder<T> infoWindowBuilder;
    private Supplier<T> newEntitySupplier;
    private EntityInputFormBuilder<T> inputFormBuilder;
    private Map<String, ContextWindowBuilder<T>> contextWindowBuilders = new LinkedHashMap<>();
    private Consumer<String> statusBarMessageAcceptor;
    private boolean isContextWindow;

    public void setInfoWindowBuilder(ContextWindowBuilder<T> infoWindowBuilder) {
        this.infoWindowBuilder = infoWindowBuilder;
    }

    public void setRequestExecutor(RequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    public void setEntitySource(EntitySource<T> entitySource) {
        this.entitySource = entitySource;
    }

    public void setEntityRemover(EntityRemover<T> entityRemover) {
        this.entityRemover = entityRemover;
    }

    public void addContextWindowBuilder(
            String contextMenuItemName,
            ContextWindowBuilder<T> contextWindowBuilder
    ) {
        contextWindowBuilders.put(contextMenuItemName, contextWindowBuilder);
    }

    public void fillContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        for (var contextMenuItemName: contextWindowBuilders.keySet()) {
            var contextWindowBuilder = contextWindowBuilders.get(contextMenuItemName);
            MenuItem menuItem = new MenuItem(contextMenuItemName);
            menuItem.setOnAction(event -> {
                T entity = entityTable.getSelectionModel().getSelectedItem();
                if (entity != null) {
                    openWindow(
                            () -> contextWindowBuilder.buildWindow(entity),
                            String.format("Не удалось выполнить действие \"%s\"", contextMenuItemName)
                    );
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
                openWindow(
                        () -> infoWindowBuilder.buildWindow(entity),
                        String.format("Не удалось открыть информацию о сущности №%d", entity.getId())
                );
            }
        });

        changeItem.setOnAction(event -> {
            T entity = entityTable.getSelectionModel().getSelectedItem();
            if (entity != null) {
                SuccessAction successAction = () -> refreshTableContents("Успешно изменено");
                Supplier<Stage> windowBuilder = () -> {
                    if (isContextWindow) {
                        return inputFormBuilder.buildContextEditFormWindow(entity, successAction);
                    }
                    return inputFormBuilder.buildEditFormWindow(entity, successAction);
                };
                openWindow(
                        windowBuilder,
                        String.format("Не удалось открыть форму изменения сущности №%d", entity.getId())
                );
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
    private ComboBox<ChoiceItem<String>> sortChoiceBox;

    @FXML
    private VBox filteringVBox;

    @FXML
    private Label pageSizeLabel;

    @FXML
    private Label totalSizeLabel;

    private final ObservableList<T> entityObservableList = FXCollections.observableArrayList();

    private Filter<T> filter;
    private PageInfo pageInfo;
    private PageSort pageSort;

    public void init(
            Map<String, String> entityPropertyNames,
            Map<String, String> entitySortPropertyNames,
            EntityInputFormBuilder<T> entityInputFormBuilder,
            Supplier<T> newEntitySupplier,
            boolean isContextWindow,
            Consumer<String> statusBarMessageAcceptor
    ) {
        this.inputFormBuilder = entityInputFormBuilder;
        this.newEntitySupplier = newEntitySupplier;
        this.isContextWindow = isContextWindow;
        this.statusBarMessageAcceptor = statusBarMessageAcceptor;

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

        ChoiceItem<String> defaultSortField = new ChoiceItem<>("id", "№");
        sortChoiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            pageSort.removeAllFields();
            if (newValue.getValue() != null) {
                pageSort.addField(newValue.getValue());
            }
        });
        sortFieldList.add(defaultSortField);
        sortChoiceBox.setValue(defaultSortField);
        sortChoiceBox.getItems().addAll(sortFieldList);

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

        fillContextMenu();

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
        SuccessAction successAction = () -> refreshTableContents("Успешно изменено");
        Supplier<Stage> windowBuilder = () -> {
            if (isContextWindow) {
                return inputFormBuilder.buildContextCreationFormWindow(
                        newEntitySupplier.get(), successAction
                );
            }
            return inputFormBuilder.buildCreationFormWindow(successAction);
        };
        openWindow(
                windowBuilder,
                "Не удалось открыть форму добавления"
        );
    }

    @FXML
    private void refreshTableContents() {
        refreshTableContents("Данные успешно загружены");
    }

    private void refreshTableContents(String successMessage) {
        disableComponent();
        requestExecutor
                .makeRequest(() -> entitySource.getEntities(pageInfo, filter))
                .setOnSuccessAction(page -> {
                    var entities = page.getElementList();
                    entities.forEach(Entity::calculateProperties);

                    Platform.runLater(() -> {
                        pageSizeLabel.setText(String.format(
                                "На странице: %d", page.getNumberOfElements()
                        ));

                        totalSizeLabel.setText(String.format(
                                "Всего: %d", page.getTotalElements()
                        ));

                        entityObservableList.clear();
                        entityObservableList.addAll(entities);
                        pagination.pageCountProperty().setValue(page.getTotalPages());
                        statusBarMessageAcceptor.accept(successMessage);
                    });
                })
                .setOnFailureAction(errorMessage -> Platform.runLater(() ->
                        AlertDialogFactory.showErrorAlertDialog(
                                "Не удалось загрузить информацию",
                                errorMessage
                        )
                ))
                .setFinalAction(() -> Platform.runLater(this::enableComponent))
                .submit();
    }

    private void openWindow(Supplier<Stage> windowBuilder, String errorDialogHeader) {
        try {
            if (windowBuilder != null) {
                Stage contextWindow = windowBuilder.get();
                contextWindow.show();
            }
        } catch (Exception e) {
            Platform.runLater(() ->
                    AlertDialogFactory.showErrorAlertDialog(
                            errorDialogHeader,
                            e.getLocalizedMessage()
                    )
            );
            e.printStackTrace();
        }
    }

    private void deleteEntity(T entity) {
        disableComponent();
        requestExecutor
                .makeRequest(() -> entityRemover.deleteEntity(entity.getId()))
                .setOnSuccessAction(responseBody -> Platform.runLater(
                        () -> refreshTableContents("Успешно удалено")
                ))
                .setOnFailureAction(errorMessage -> {
                    Platform.runLater(() -> {
                        enableComponent();
                        AlertDialogFactory.showErrorAlertDialog(
                                String.format("Не удалось удалить сущность № %d!", entity.getId()),
                                errorMessage
                        );
                    });
                })
                .submit();
    }

    private void disableComponent() {
        rootVBox.setDisable(true);
    }

    private void enableComponent() {
        rootVBox.setDisable(false);
    }

}
