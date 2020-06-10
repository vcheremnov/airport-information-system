package app.gui.controllers;

import app.gui.custom.ChoiceBoxItem;
import app.gui.custom.DateTimePicker;
import app.model.Entity;
import app.services.ServiceResponse;
import app.utils.LocalDateFormatter;
import app.utils.RequestExecutor;
import com.sun.javafx.scene.control.InputField;
import com.sun.javafx.scene.control.IntegerField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.*;

public class EntityCreationController<T extends Entity> {

    interface EntityCreator<E extends Entity> {
        ServiceResponse<E> createEntity(E entity) throws Exception;
    }

    interface EntityFieldSetter<E extends Entity, X> {
        void setField(E entity, X fieldValue);
    }

    interface ChoiceBoxItemSupplier<X> {
        Collection<ChoiceBoxItem<X>> getItems();
    }

    private EntityCreator<T> entityCreator;
    private RequestExecutor requestExecutor;


    private final List<TextField> textFields = new ArrayList<>();
    private final List<IntegerField> integerFields = new ArrayList<>();
    private final List<DateTimePicker> dateTimePickers = new ArrayList<>();

    private final Map<ChoiceBox, ChoiceBoxItem> choiceBoxes = new HashMap<>();

    @FXML
    private Button createButton;

    @FXML
    private Label statusBarLabel;

    @FXML
    private GridPane grid;

    private T entity;

    public void init(
            T entity,
            EntityCreator<T> entityCreator,
            RequestExecutor requestExecutor
    ) {
        this.entity = entity;
        this.entityCreator = entityCreator;
        this.requestExecutor = requestExecutor;
    }

    public void addTextField(
            String name,
            EntityFieldSetter<T, String> stringFieldSetter
    ) {
        TextField textField = new TextField();
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            stringFieldSetter.setField(entity, newValue);
            System.out.println(newValue);
        });

        addField(name, textField);
        textFields.add(textField);
    }

    public void addIntegerField(
            String name,
            EntityFieldSetter<T, Integer> integerFieldSetter
    ) {
        addIntegerField(name, integerFieldSetter, Integer.MAX_VALUE);
    }

    public void addIntegerField(
            String name,
            EntityFieldSetter<T, Integer> integerFieldSetter,
            int maxValue
    ) {
        IntegerField integerField = new IntegerField(maxValue);
        integerField.valueProperty().setValue(null);
        integerField.valueProperty().addListener((observable, oldValue, newValue) -> {
            integerFieldSetter.setField(entity, (Integer) newValue);
            System.out.println(newValue);
        });

        addField(name, integerField);
        integerFields.add(integerField);
    }

    public void addDateField(
            String name,
            EntityFieldSetter<T, Date> dateFieldSetter
    ) {
        addDateTimePicker(name, dateFieldSetter, LocalDateFormatter.getDateFormat());
    }

    public void addDateTimeField(
            String name,
            EntityFieldSetter<T, Date> dateFieldSetter
    ) {
        addDateTimePicker(name, dateFieldSetter, LocalDateFormatter.getDateTimeFormat());
    }

    private void addDateTimePicker(
            String name,
            EntityFieldSetter<T, Date> dateFieldSetter,
            String timeFormat
    ) {
        DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setFormat(timeFormat);
        dateTimePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            dateFieldSetter.setField(
                    entity, newValue == null ? null : java.sql.Date.valueOf(newValue)
            );
            System.out.println(
                    LocalDateFormatter.getFormattedTimestamp(
                            newValue == null ? null : java.sql.Timestamp.valueOf(dateTimePicker.getDateTimeValue())));
        });

        addField(name, dateTimePicker);
        dateTimePickers.add(dateTimePicker);
    }

    public <X> void addChoiceBox(
            String name,
            EntityFieldSetter<T, X> fieldSetter,
            ChoiceBoxItemSupplier<X> itemSupplier

    ) {
        ChoiceBoxItem<X> defaultItem = new ChoiceBoxItem<>(null, "Не указано");
        var items = itemSupplier.getItems();
        items.add(defaultItem);

        ChoiceBox<ChoiceBoxItem<X>> choiceBox = new ChoiceBox<>();
        choiceBox.setValue(defaultItem);
        choiceBox.getItems().addAll(items);
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            fieldSetter.setField(entity, newValue.getKey());
            System.out.println(newValue.getKey());
        });

        choiceBoxes.put(choiceBox, defaultItem);
        addField(name, choiceBox);
    }

    private void addField(String name, Control field) {
        field.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Label label = new Label(String.format("%s:", name));
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        GridPane.setHalignment(field, HPos.CENTER);
        GridPane.setValignment(field, VPos.CENTER);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setValignment(label, VPos.CENTER);

        int rowsNumber = grid.getRowCount();
        grid.add(label, 0, rowsNumber, 1, 1);
        grid.add(field, 1, rowsNumber, 2, 1);
    }

    private boolean validateFields() {
        System.out.println("VALIDATION");

        for (var textField: textFields) {
            var text = textField.getText().trim();
            System.out.println("TEXT VALUE: " + text);
            if (text.isEmpty()) {
                textField.requestFocus();
                return false;
            }
        }

        for (var integerField: integerFields) {
            var value = integerField.valueProperty().getValue();
            System.out.println("integer value: " + value);
            if (value == null) {
                integerField.requestFocus();
                return false;
            }
        }

        for (var dateTimePicker: dateTimePickers) {
            var date = dateTimePicker.valueProperty().getValue();
            System.out.println("date value: " + date);
            if (date == null) {
                dateTimePicker.requestFocus();
                return false;
            }
        }

        for (var rawChoiceBox: choiceBoxes.keySet()) {
            ChoiceBox<ChoiceBoxItem<?>> choiceBox = rawChoiceBox;
            ChoiceBoxItem<?> choiceBoxItem = choiceBox.valueProperty().getValue();
            System.out.println("choice box item value: " + choiceBoxItem.getKey());
            if (choiceBoxItem.getKey() == null) {
                choiceBox.requestFocus();
                return false;
            }
        }

        return true;
    }

    @FXML
    private void createEntity(ActionEvent event) {
        boolean fieldsAreValid = validateFields();
        if (!fieldsAreValid) {
            System.out.println("NOT OK!");
            return;
        }

        System.out.println("OK!");

        requestExecutor
                .makeRequest(() -> entityCreator.createEntity(entity))
                .setOnSuccessAction(createdEntity -> Platform.runLater(() -> {
                    Node sourceNode = (Node) event.getSource();
                    Stage stage = (Stage) sourceNode.getScene().getWindow();
                    stage.close();
                }))
                .setOnFailureAction(this::setStatusBarMessage)
                .submit();
    }

    @FXML
    private void clearFields() {
        for (var textField: textFields) {
            textField.setText("");
        }

        for (var integerField: integerFields) {
            integerField.valueProperty().setValue(null);
        }

        for (var dateTimePicker: dateTimePickers) {
            dateTimePicker.valueProperty().setValue(null);
        }

        for (var rawChoiceBox: choiceBoxes.keySet()) {
            ChoiceBox<ChoiceBoxItem<?>> choiceBox = rawChoiceBox;
            ChoiceBoxItem<?> defaultItem = choiceBoxes.get(rawChoiceBox);
            choiceBox.setValue(defaultItem);
        }
    }


    private void setStatusBarMessage(String message) {
        Platform.runLater(() -> {
            String messageTime = LocalDateFormatter.getFormattedTimestamp(Instant.now().toEpochMilli());
            String messageWithTime = String.format("%s: %s", messageTime, message);
            statusBarLabel.textProperty().setValue(messageWithTime);
        });
    }

}
