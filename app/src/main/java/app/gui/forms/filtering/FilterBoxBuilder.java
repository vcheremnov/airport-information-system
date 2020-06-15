package app.gui.forms.filtering;

import app.model.Entity;
import app.services.filters.Filter;
import javafx.scene.Node;

public interface FilterBoxBuilder<T extends Entity> {

    Node buildFilterBox(Filter<T> filter);

}
