package airport.services.impl;

import airport.dtos.AirplaneTypeDto;
import airport.entities.AirplaneType;
import airport.mappers.Mapper;
import airport.repositories.AirplaneTypeRepository;
import airport.services.AirplaneTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AirplaneTypeServiceImpl
        extends AbstractService<AirplaneType, AirplaneTypeDto, Long>
        implements AirplaneTypeService {

    private final AirplaneTypeRepository repository;
    private final Mapper<AirplaneType, AirplaneTypeDto, Long> mapper;

    @Autowired
    public AirplaneTypeServiceImpl(AirplaneTypeRepository repository,
                                   Mapper<AirplaneType, AirplaneTypeDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<AirplaneType, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<AirplaneType, AirplaneTypeDto, Long> getMapper() {
        return mapper;
    }
}
