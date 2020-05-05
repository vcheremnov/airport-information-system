package airport.services.impl;

import airport.dtos.PassengerDto;
import airport.dtos.PassengerDto;
import airport.entities.Passenger;
import airport.entities.Passenger;
import airport.mappers.Mapper;
import airport.repositories.PassengerRepository;
import airport.services.PassengerService;
import airport.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

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

}
