package app.gui.controllers.interfaces;

import app.model.Entity;

public interface ContextMenuAction<E extends Entity> {
    void run(E entity);
}
