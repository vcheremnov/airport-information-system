package airport.services.impl;

import airport.dtos.PassengerDto;
import airport.dtos.PassengerDto;
import airport.entities.Passenger;
import airport.entities.Passenger;
import airport.filters.PassengerFilter;
import airport.mappers.Mapper;
import airport.repositories.PassengerRepository;
import airport.services.PassengerService;
import airport.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;

@Service
public class PassengerServiceImpl
        extends AbstractService<Passenger, PassengerDto, Long>
        implements PassengerService {

    private final PassengerRepository repository;
    private final Mapper<Passenger, PassengerDto, Long> mapper;

    @Autowired
    public PassengerServiceImpl(PassengerRepository repository,
                           Mapper<Passenger, PassengerDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<Passenger, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Passenger, PassengerDto, Long> getMapper() {
        return mapper;
    }

    @Override
    public Page<PassengerDto> search(PassengerFilter filter, Pageable pageable) {
        return repository.findAllByFilter(
            filter.getFlightId(),
            filter.getMinFlightDate(),
            filter.getMaxFlightDate(),
            filter.getSex(),
            filter.getMinBirthDate(),
            filter.getMaxBirthDate(),
            pageable
        ).map(mapper::toDto);
    }
}
