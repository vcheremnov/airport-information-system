package airport.services;

import airport.dtos.FlightDto;
import airport.dtos.PassengerDto;
import airport.dtos.TicketDto;
import airport.entities.types.TicketStatus;
import airport.filters.FlightFilter;
import airport.filters.TicketFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightService extends Service<FlightDto, Long> {
    Page<FlightDto> search(FlightFilter filter, Pageable pageable);
}
