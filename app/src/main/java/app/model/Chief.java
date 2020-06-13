package app.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Chief extends Person {

    @Override
    public Chief clone() {
        return (Chief) super.clone();
    }

}
