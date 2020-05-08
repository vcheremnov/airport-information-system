package airport.services;

import airport.dtos.TicketDto;
import airport.filters.TicketFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TicketService extends Service<TicketDto, Long> {
    Page<TicketDto> getTickets(TicketFilter filter, Pageable pageable);
}
