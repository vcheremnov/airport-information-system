package airport.services;

import airport.dtos.PassengerDto;
import airport.filters.FlightFilter;
import airport.filters.PassengerFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PassengerService extends Service<PassengerDto, Long> {
    Page<PassengerDto> search(PassengerFilter filter, Pageable pageable);
}
