package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DepartmentDto extends AbstractDto<Long> {

    private String name;
    private ChiefDto chief;

}
