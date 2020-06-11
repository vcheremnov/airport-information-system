package app.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;


public class EntityInfoController {

    @FXML
    private ListView<String> listView;

    public void addInfoLine(String line) {
        Platform.runLater(() -> listView.getItems().add(line));
    }

}
