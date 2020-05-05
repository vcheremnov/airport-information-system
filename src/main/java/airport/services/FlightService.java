package airport.services;

import airport.dtos.FlightDto;
import airport.filters.FlightFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightService extends Service<FlightDto, Long> {
    Page<FlightDto> search(Pageable pageable, FlightFilter filter);
}
