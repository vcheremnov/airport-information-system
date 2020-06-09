package app.gui;

import app.gui.controllers.FxmlLoaderFactory;
import app.gui.controllers.MainController;
import app.utils.RequestExecutor;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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

        FXMLLoader mainLoader = FxmlLoaderFactory.createMainViewLoader();
        Parent root = mainLoader.load();
        MainController mainController = mainLoader.getController();
        mainController.init(requestExecutor);

        Scene scene = new Scene(root);
        stage.setTitle("Airport information system");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setOnCloseRequest(event -> {
            requestExecutor.shutdown();
            Platform.exit();
        });
        stage.show();

    }
}
