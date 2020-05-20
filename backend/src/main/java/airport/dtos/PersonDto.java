package airport.dtos;

import airport.entities.types.Sex;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class PersonDto extends AbstractDto<Long> {

    private String name;
    private Date birthDate;
    private Sex sex;

}
