package app.gui.forms;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StageFactory {

    public static Stage createStage(Parent rootNode, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.sizeToScene();
        return stage;
    }

}
