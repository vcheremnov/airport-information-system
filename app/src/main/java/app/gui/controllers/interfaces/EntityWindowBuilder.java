package app.gui.controllers.interfaces;

import app.model.Entity;
import javafx.stage.Stage;

public interface EntityWindowBuilder<E extends Entity> {
    Stage buildWindow(E entity) throws Exception;
}