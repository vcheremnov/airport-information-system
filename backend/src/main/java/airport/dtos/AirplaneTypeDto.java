package airport.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AirplaneTypeDto extends AbstractDto<Long> {

    private String name;
    private Integer capacity;
    private Integer speed;

}
