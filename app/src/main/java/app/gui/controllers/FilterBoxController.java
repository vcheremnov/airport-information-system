package app.gui.controllers;

import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.gui.custom.DateTimePicker;
import app.utils.LocalDateFormatter;
import com.sun.javafx.scene.control.DoubleField;
import com.sun.javafx.scene.control.IntegerField;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.function.UnaryOperator;

public class FilterBoxController<T> {

    public void init() {
        contentBox.setStyle(
                "-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: #3c3f4b;"
        );
    }

    public interface EntityFieldSetter<X> {
        void setField(X value);
    }

    private final List<TextField> textFields = new ArrayList<>();
    private final List<TextField> integerFields = new ArrayList<>();
    private final List<DateTimePicker> dateTimePickers = new ArrayList<>();
    private final List<CheckBox> checkBoxes = new ArrayList<>();
    private final Map<ComboBox, ChoiceItem> choiceBoxes = new LinkedHashMap<>();

    @FXML
    private VBox contentBox;

    @FXML
    private GridPane grid;

    @FXML
    private ColumnConstraints columnConstraints;

    @FXML
    private RowConstraints rowConstraints;

    public void setNumberOfCols(int colsNumber) {
        grid.getColumnConstraints().clear();
        for (int i = 0; i < colsNumber; i++) {
            grid.getColumnConstraints().add(columnConstraints);
        }
    }

    public void setNumberOfRows(int rowsNumber) {
        grid.getRowConstraints().clear();
        for (int i = 0; i < rowsNumber; i++) {
            grid.getRowConstraints().add(rowConstraints);
        }
    }

    public void addTextField(
            EntityFieldSetter<String> fieldSetter,
            int columnIndex, int rowIndex, int colSpan
    ) {
        TextField textField = new TextField();
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            fieldSetter.setField(newValue.trim());
        });

        addField(textField, columnIndex, rowIndex, colSpan);
        textFields.add(textField);
    }

    public void addIntegerField(
            EntityFieldSetter<Integer> fieldSetter,
            int columnIndex, int rowIndex, int colSpan
    ) {
        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("0|([1-9][0-9]{0,8})?")) {
                return change;
            }
            return null;
        };

        TextField integerField = new TextField();
        integerField.setTextFormatter(
                new TextFormatter<>(
                        new IntegerStringConverter(), null, integerFilter
                )
        );

        integerField.textProperty().addListener((observable, oldValue, newValue) -> {
            fieldSetter.setField(newValue.isEmpty() ? null : Integer.valueOf(newValue));
        });

        addField(integerField, columnIndex, rowIndex, colSpan);
        integerFields.add(integerField);
    }

    public void addDateField(
            EntityFieldSetter<Date> fieldSetter,
            int columnIndex, int rowIndex, int colSpan
    ) {
        DateTimePicker dateTimePicker = new DateTimePicker();
        dateTimePicker.setFormat(LocalDateFormatter.getDateFormat());
        dateTimePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalDateTime localDateTime = dateTimePicker.getDateTimeValue();
            Date date = localDateTime == null ?
                    null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

            fieldSetter.setField(date);
        });

        addField(dateTimePicker, columnIndex, rowIndex, colSpan);
        dateTimePickers.add(dateTimePicker);
    }

    @SneakyThrows
    public <X> void addChoiceBox(
            EntityFieldSetter<X> fieldSetter,
            ChoiceItemSupplier<X> itemSupplier,
            int columnIndex, int rowIndex, int colSpan

    ) {
        ChoiceItem<X> defaultItem = new ChoiceItem<>(null, "...");
        var items = itemSupplier.getItems();
        items.add(defaultItem);

        ComboBox<ChoiceItem<X>> choiceBox = new ComboBox<>();
        choiceBox.getItems().addAll(items);
        choiceBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            fieldSetter.setField(newValue.getValue());
        });
        choiceBox.setValue(defaultItem);

        choiceBoxes.put(choiceBox, defaultItem);
        addField(choiceBox, columnIndex, rowIndex, colSpan);
    }

    public void addLabel(
            String text,
            int columnIndex, int rowIndex, int colSpan
    ) {
        addLabel(text, Pos.CENTER_LEFT, columnIndex, rowIndex, colSpan);
    }

    public void addLabel(
            String text,
            Pos alignment,
            int columnIndex, int rowIndex, int colSpan
    ) {
        Label label = new Label(text);
        label.setAlignment(alignment);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setValignment(label, VPos.CENTER);
        grid.add(label, columnIndex, rowIndex, colSpan, 1);
    }

    private void addField(
            Control field,
            int columnIndex, int rowIndex, int colSpan
    ) {
        field.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        GridPane.setHalignment(field, HPos.CENTER);
        GridPane.setValignment(field, VPos.CENTER);

        grid.add(field, columnIndex, rowIndex, colSpan, 1);
    }

    @FXML
    private void clearFields() {
        for (var textField: textFields) {
            textField.setText("");
        }

        for (var integerField: integerFields) {
            integerField.setText("");
        }

        for (var dateTimePicker: dateTimePickers) {
            dateTimePicker.valueProperty().setValue(null);
        }

        for (var rawChoiceBox: choiceBoxes.keySet()) {
            ComboBox<ChoiceItem<?>> choiceBox = rawChoiceBox;
            ChoiceItem<?> defaultItem = choiceBoxes.get(rawChoiceBox);
            choiceBox.setValue(defaultItem);
        }

        for (var checkBox: checkBoxes) {
            checkBox.setSelected(false);
        }
    }

}
