package app.gui.controllers;

import app.ServiceFactory;
import app.model.Entity;
import app.model.Passenger;
import app.services.PassengerService;
import app.services.Service;
import app.services.ServiceResponse;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;
import app.services.pagination.PageSort;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EntityTableController<T extends Entity> {

    @FXML
    private Pagination pagination;

    @FXML
    private TableView<T> entityTable;

    @FXML
    private VBox filteringVBox;

    @FXML
    private Button refreshButton;

    private final ObservableList<T> entityObservableList = FXCollections.observableArrayList();

    private Service<T> entityService;
    private PageInfo pageInfo;
    private PageSort pageSort;

    public void init(
            Map<String, String> localizedColumns,
            Service<T> entityService
    ) {
        pageSort = new PageSort();
        pageInfo = new PageInfo(0L, 23L, pageSort);
        this.entityService = entityService;

        List<TableColumn<T, String>> columns = localizedColumns
                .entrySet()
                .stream()
                .map(e -> {
                    TableColumn<T, String> tableColumn = new TableColumn<>(e.getValue());
                    tableColumn.setCellValueFactory(new PropertyValueFactory<>(e.getKey()));
                    return tableColumn;
                }).collect(Collectors.toList());

        entityTable.getColumns().addAll(columns);
        entityTable.setItems(entityObservableList);
    }

    @FXML
    void refreshTableContents(ActionEvent event) {

        RequestHandler.handleRequest(
                () -> entityService.getAll(pageInfo),
                page -> Platform.runLater(() -> {
                    var entities = page.getElementList();
                    entityObservableList.clear();
                    entityObservableList.addAll(entities);
                }),
                errorMessage -> System.err.println("FAILURE")
        );

    }

}
