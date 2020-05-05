package airport.services.impl;

import airport.dtos.FlightDelayDto;
import airport.dtos.FlightDelayDto;
import airport.entities.FlightDelay;
import airport.entities.FlightDelay;
import airport.mappers.Mapper;
import airport.repositories.FlightDelayRepository;
import airport.services.FlightDelayService;
import airport.services.FlightDelayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightDelayServiceImpl
        extends AbstractService<FlightDelay, FlightDelayDto, Long>
        implements FlightDelayService {

    private final FlightDelayRepository repository;
    private final Mapper<FlightDelay, FlightDelayDto, Long> mapper;

    @Autowired
    public FlightDelayServiceImpl(FlightDelayRepository repository,
                           Mapper<FlightDelay, FlightDelayDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<FlightDelay, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<FlightDelay, FlightDelayDto, Long> getMapper() {
        return mapper;
    }

}
