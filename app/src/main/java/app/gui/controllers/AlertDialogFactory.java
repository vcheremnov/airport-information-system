package app.gui.controllers;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import lombok.experimental.UtilityClass;

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

    private void showAlertDialog(
            Alert.AlertType alertType,
            String title,
            String headerText,
            String contentText
    ) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);
            alert.showAndWait();
        });
    }

}
