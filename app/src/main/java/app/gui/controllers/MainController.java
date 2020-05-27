package app.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {

    private Stage stage;

    @FXML
    private TabPane contentTabPane;

    public void init(Stage stage) {
        this.stage = stage;

    }

    public void setTable(Node tabComponent, String tabName) {
        Tab newTab = new Tab(tabName);
        newTab.setContent(tabComponent);
        contentTabPane.getTabs().add(newTab);
    }

}
