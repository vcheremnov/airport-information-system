package app.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class City extends Entity {

    private String name;
    private Integer distance;

}
