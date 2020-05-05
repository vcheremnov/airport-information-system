package airport.services.impl;

import airport.dtos.AirplaneTypeDto;
import airport.dtos.ChiefDto;
import airport.entities.AirplaneType;
import airport.entities.Chief;
import airport.mappers.Mapper;
import airport.repositories.AirplaneTypeRepository;
import airport.repositories.ChiefRepository;
import airport.services.ChiefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ChiefServiceImpl
        extends AbstractService<Chief, ChiefDto, Long>
        implements ChiefService {

    private final ChiefRepository repository;
    private final Mapper<Chief, ChiefDto, Long> mapper;

    @Autowired
    public ChiefServiceImpl(ChiefRepository repository,
                                   Mapper<Chief, ChiefDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<Chief, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Chief, ChiefDto, Long> getMapper() {
        return mapper;
    }

}
