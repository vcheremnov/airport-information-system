package airport.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CityDto extends AbstractDto<Long> {

    private String name;
    private Integer distance;

}
