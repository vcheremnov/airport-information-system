package app.gui.forms.filtering.impl;

import app.gui.AlertDialogFactory;
import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.FilterBoxController;
import app.gui.controllers.FxmlLoaderFactory;
import app.gui.forms.filtering.FilterBoxBuilder;
import app.model.Airplane;
import app.model.Entity;
import app.services.filters.Filter;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import lombok.SneakyThrows;

import java.util.function.Supplier;

public abstract class AbstractFilterBoxBuilder<T extends Entity> implements FilterBoxBuilder<T> {

    @Override
    @SneakyThrows
    public Node buildFilterBox(Filter<T> filter) {
        var fxmlLoader = FxmlLoaderFactory.createFilterBoxLoader();
        Parent rootNode = fxmlLoader.load();
        FilterBoxController<T> controller = fxmlLoader.getController();
        controller.init();
        fillFilterBox(controller, filter);
        return rootNode;
    }

    protected abstract void fillFilterBox(FilterBoxController<T> controller, Filter<T> filter);

}
