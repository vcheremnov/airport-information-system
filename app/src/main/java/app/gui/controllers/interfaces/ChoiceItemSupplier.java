package app.gui.controllers.interfaces;

import app.gui.custom.ChoiceItem;

import java.util.Collection;

public interface ChoiceItemSupplier<X> {
    Collection<ChoiceItem<X>> getItems() throws Exception;
}