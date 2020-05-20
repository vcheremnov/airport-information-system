package app.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Department extends Entity {

    private String name;
    private Chief chief;

}
