package app.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Passenger extends Person {

    @Override
    public Passenger clone() {
        return (Passenger) super.clone();
    }

}
