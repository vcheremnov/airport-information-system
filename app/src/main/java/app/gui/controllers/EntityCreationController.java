package app.gui.controllers;

import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.gui.custom.DateTimePicker;
import app.model.Entity;
import app.services.ServiceResponse;
import app.utils.LocalDateFormatter;
import app.utils.RequestExecutor;
import com.sun.javafx.scene.control.IntegerField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class EntityCreationController<T extends Entity> {

    interface EntityCreator<E extends Entity> {
        ServiceResponse<E> createEntity(E entity) throws Exception;
    }

    interface EntityFieldSetter<E extends Entity, X> {
        void setField(E entity, X fieldValue);
    }

    private EntityCreator<T> entityCreator;
    private RequestExecutor requestExecutor;

    private final List<TextField> textFields = new ArrayList<>();
    private final List<IntegerField> integerFields = new ArrayList<>();
    private final List<DateTimePicker> dateTimePickers = new ArrayList<>();

    private final Map<ComboBox, ChoiceItem> choiceBoxes = new LinkedHashMap<>();

    @FXML
    private VBox contentBox;

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
            stringFieldSetter.setField(entity, newValue.trim());
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
            LocalDateTime localDateTime = dateTimePicker.getDateTimeValue();
            Date date = localDateTime == null ?
                    null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            dateFieldSetter.setField(entity, date);
        });

        addField(name, dateTimePicker);
        dateTimePickers.add(dateTimePicker);
    }

    @SneakyThrows
    public <X> void addChoiceBox(
            String name,
            EntityFieldSetter<T, X> fieldSetter,
            ChoiceItemSupplier<X> itemSupplier

    ) {
        ChoiceItem<X> defaultItem = new ChoiceItem<>(null, "Не указано");
        var items = itemSupplier.getItems();
        items.add(defaultItem);

        ComboBox<ChoiceItem<X>> choiceBox = new ComboBox<>();
        choiceBox.setValue(defaultItem);
        choiceBox.getItems().addAll(items);
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            fieldSetter.setField(entity, newValue.getItem());
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
        grid.add(label, 0, rowsNumber, 2, 1);
        grid.add(field, 2, rowsNumber, 3, 1);
    }

    private boolean validateFields() {
        for (var textField: textFields) {
            var text = textField.getText().trim();
            if (text.isEmpty()) {
                textField.setText("");
                textField.requestFocus();
                return false;
            }
        }

        for (var integerField: integerFields) {
            var value = integerField.valueProperty().getValue();
            if (value == null) {
                integerField.requestFocus();
                return false;
            }
        }

        for (var dateTimePicker: dateTimePickers) {
            var date = dateTimePicker.valueProperty().getValue();
            if (date == null) {
                dateTimePicker.requestFocus();
                return false;
            }
        }

        for (var rawChoiceBox: choiceBoxes.keySet()) {
            ComboBox<ChoiceItem<?>> choiceBox = rawChoiceBox;
            ChoiceItem<?> choiceItem = choiceBox.valueProperty().getValue();
            if (choiceItem.getItem() == null) {
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
            return;
        }

        disableComponent();
        requestExecutor
                .makeRequest(() -> entityCreator.createEntity(entity))
                .setOnSuccessAction(createdEntity -> Platform.runLater(() -> {
                    Node sourceNode = (Node) event.getSource();
                    Stage stage = (Stage) sourceNode.getScene().getWindow();
                    stage.close();
                }))
//                .setOnFailureAction(this::setStatusBarMessage)
                .setFinalAction(this::enableComponent)
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
            ComboBox<ChoiceItem<?>> choiceBox = rawChoiceBox;
            ChoiceItem<?> defaultItem = choiceBoxes.get(rawChoiceBox);
            choiceBox.setValue(defaultItem);
        }
    }

    private void enableComponent() {
        contentBox.setDisable(false);
    }

    private void disableComponent() {
        contentBox.setDisable(true);
    }

}
