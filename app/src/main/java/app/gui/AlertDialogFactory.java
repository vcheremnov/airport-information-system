package app.gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class AlertDialogFactory {

    public void showInfoAlertDialog(
            String headerText,
            String contentText
    ) {
        showAlertDialog(Alert.AlertType.INFORMATION, "Информация", headerText, contentText);
    }

    public void showWarningAlertDialog(
            String headerText,
            String contentText
    ) {
        showAlertDialog(Alert.AlertType.WARNING, "Внимание!", headerText, contentText);
    }

    public void showErrorAlertDialog(
            String headerText,
            String contentText
    ) {
        showAlertDialog(Alert.AlertType.ERROR, "Ошибка!", headerText, contentText);
    }

    public void showConfirmationDialog(
            String headerText,
            String contextText,
            Runnable onConfirmationAction
    ) {
        Platform.runLater(() -> {
            Alert alert = createAlertDialog(
                    Alert.AlertType.CONFIRMATION,
                    "Подтверждение действия",
                    headerText,
                    contextText
            );
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                onConfirmationAction.run();
            }
        });
    }

    private void showAlertDialog(
            Alert.AlertType alertType,
            String title,
            String headerText,
            String contentText
    ) {
        Platform.runLater(() -> {
            Alert alert = createAlertDialog(alertType, title, headerText, contentText);
            alert.showAndWait();
        });
    }

    private Alert createAlertDialog(
            Alert.AlertType alertType,
            String title,
            String headerText,
            String contentText
    ) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert;
    }

}
