package app.gui.forms;

import app.model.Entity;
import javafx.stage.Stage;

public interface EntityInputFormBuilder<E extends Entity> {

    Stage buildCreationFormWindow();

    Stage buildEditFormWindow(E entity);

}
