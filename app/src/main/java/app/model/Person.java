package app.model;

import app.model.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Person extends Entity {

    private String name;
    private Date birthDate;
    private Sex sex;

}
