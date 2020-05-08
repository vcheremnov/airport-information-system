package airport.services.impl;

import airport.dtos.FlightDelayDto;
import airport.dtos.FlightDto;
import airport.entities.Flight;
import airport.entities.FlightDelay;
import airport.entities.types.FlightDelayReason;
import airport.filters.FlightFilter;
import airport.mappers.Mapper;
import airport.repositories.FlightRepository;
import airport.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class FlightServiceImpl
        extends AbstractService<Flight, FlightDto, Long>
        implements FlightService {

    private final FlightRepository repository;
    private final Mapper<Flight, FlightDto, Long> mapper;

    @Autowired
    public FlightServiceImpl(FlightRepository repository,
                             Mapper<Flight, FlightDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<FlightDto> search(FlightFilter filter, Pageable pageable) {
        return repository.searchByFilter(
                filter.getAirplaneId(),
                filter.getFlightType(),
                filter.getAirplaneTypeId(),
                filter.getCityId(),
                filter.getIsCancelled(),
                filter.getIsDelayed(),
                filter.getDelayReason(),
                filter.getMinDate(),
                filter.getMaxDate(),
                filter.getMinDuration(),
                filter.getMaxDuration(),
                filter.getMinTicketPrice(),
                filter.getMaxTicketPrice(),
//                filter.getMinSoldSeatsPercentage(),
//                filter.getMaxSoldSeatsPercentage(),
                pageable
        ).map(mapper::toDto);
    }

    @Override
    @Transactional
    public FlightDto delayFlight(Long flightId, Timestamp newFlightTime, FlightDelayReason reason) {
        Flight flight = getEntityByIdOrThrow(flightId);
        flight.setFlightTime(newFlightTime);

        FlightDelay flightDelay = new FlightDelay();
        flightDelay.setId(flightId);
        flightDelay.setFlight(flight);
        flightDelay.setDelayReason(reason);
        flight.setFlightDelay(flightDelay);

        flight = repository.save(flight);
        return mapper.toDto(flight);
    }

    @Override
    protected JpaRepository<Flight, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Flight, FlightDto, Long> getMapper() {
        return mapper;
    }
}
