package app.gui;

import app.ServiceFactory;
import app.gui.controllers.EntityTableController;
import app.gui.controllers.MainController;
import app.gui.controllers.RequestExecutor;
import app.model.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class JavaFxApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        RequestExecutor requestExecutor = new RequestExecutor();

        FXMLLoader mainLoader = new FXMLLoader();
        URL mainFxmlLocation = getClass().getClassLoader().getResource("gui/main_view.fxml");
        mainLoader.setLocation(mainFxmlLocation);
        Parent root = mainLoader.load();
        MainController mainController = mainLoader.getController();

        FXMLLoader tableLoader = new FXMLLoader();
        URL entityTableLocation = getClass().getClassLoader().getResource("gui/entity_table.fxml");
        tableLoader.setLocation(entityTableLocation);
        Node table = tableLoader.load();

        EntityTableController<MedicalExamination> controller = tableLoader.getController();
        controller.init(
                MedicalExamination.getPropertyNames(),
                requestExecutor,
                ServiceFactory.getMedicalExaminationService()
        );

        mainController.setTable(table, "TEST");

        Scene scene = new Scene(root);
        stage.setTitle("Airport information system");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        stage.setOnCloseRequest(event -> {
            requestExecutor.shutdown();
            Platform.exit();
        });
    }
}
