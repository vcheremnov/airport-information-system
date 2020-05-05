package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class TeamDto extends AbstractDto<Long> {

    private Long departmentId;

}
