package app.gui.custom;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChoiceBoxItem<X> {

    private final X key;
    private final String value;

    @Override
    public String toString() {
        return value;
    }

}