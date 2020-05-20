package app.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AirplaneType extends Entity {

    private String name;
    private Integer capacity;
    private Integer speed;

}
