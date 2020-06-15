package app.gui.forms.input;

import app.gui.controllers.interfaces.SuccessAction;
import app.model.Entity;
import javafx.stage.Stage;

public interface EntityInputFormBuilder<E extends Entity> {

    Stage buildCreationFormWindow(SuccessAction onSuccessAction);

    Stage buildEditFormWindow(E entity, SuccessAction onSuccessAction);

    Stage buildContextCreationFormWindow(E entity, SuccessAction onSuccessAction);

    Stage buildContextEditFormWindow(E entity, SuccessAction onSuccessAction);

}
