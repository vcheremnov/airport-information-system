package app.gui.controllers;

import javafx.fxml.FXMLLoader;
import lombok.experimental.UtilityClass;

import java.net.URL;

@UtilityClass
public class FxmlLoaderFactory {

    public FXMLLoader createMainViewLoader() {
        return createFxmlLoader("/gui/main_view.fxml");
    }

    public FXMLLoader createEntityTableLoader() {
        return createFxmlLoader("/gui/entity_table.fxml");
    }

    public FXMLLoader createEntityInputFormLoader() {
        return createFxmlLoader("/gui/entity_input_form.fxml");
    }

    public FXMLLoader createFilterBoxLoader() {
        return createFxmlLoader("/gui/filter_box.fxml");
    }

    public FXMLLoader createEntityInfoLoader() {
        return createFxmlLoader("/gui/entity_info.fxml");
    }

    private FXMLLoader createFxmlLoader(String fxmlName) {
        FXMLLoader createLoader = new FXMLLoader();
        URL fxmlLocation = FxmlLoaderFactory.class.getResource(fxmlName);
        createLoader.setLocation(fxmlLocation);
        return createLoader;
    }

}
