package airport.dtos;

import airport.entities.types.TicketStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TicketDto extends AbstractDto<Long> {

    private Long passengerId;
    private Long flightId;
    private TicketStatus status;

}
