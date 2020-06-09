package airport.services;

import airport.dtos.FlightDelayDto;
import airport.dtos.FlightDto;
import airport.dtos.PassengerDto;
import airport.dtos.TicketDto;
import airport.entities.types.FlightDelayReason;
import airport.entities.types.TicketStatus;
import airport.filters.FlightFilter;
import airport.filters.TicketFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface FlightService extends Service<FlightDto, Long> {
    Page<FlightDto> search(FlightFilter filter, Pageable pageable);

    FlightDto delayFlight(Long flightId, Timestamp newFlightTime, FlightDelayReason reason);

    Page<TicketDto> getTickets(Long flightId, Pageable pageable);
}
