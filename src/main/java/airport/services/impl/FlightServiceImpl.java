package airport.services.impl;

import airport.dtos.FlightDto;
import airport.entities.Flight;
import airport.filters.FlightFilter;
import airport.mappers.Mapper;
import airport.repositories.FlightRepository;
import airport.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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
                pageable
        ).map(mapper::toDto);
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
