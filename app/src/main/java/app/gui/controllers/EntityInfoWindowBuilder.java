package app.gui.controllers;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class EntityInfoWindowBuilder {

    public static Builder newInfoWindow(String windowName) {
        return new Builder(windowName);
    }

    public static class Builder {

        private final Stage stage;
        private final TabPane tabPane;

        private Builder(String windowName) {
            stage = new Stage();
            stage.setTitle(windowName);

            tabPane = new TabPane();
            Scene scene = new Scene(tabPane);
            stage.setScene(scene);
        }

        public Builder addTab(Node contentNode, String tabName) {
            Tab tab = new Tab(tabName);
            tab.setContent(contentNode);
            tab.setClosable(false);
            tabPane.getTabs().add(tab);
            return this;
        }

        public Stage build() {
            return stage;
        }

    }

}
