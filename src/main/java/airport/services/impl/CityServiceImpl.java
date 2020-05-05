package airport.services.impl;

import airport.dtos.ChiefDto;
import airport.dtos.CityDto;
import airport.entities.Chief;
import airport.entities.City;
import airport.mappers.Mapper;
import airport.repositories.ChiefRepository;
import airport.repositories.CityRepository;
import airport.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl
        extends AbstractService<City, CityDto, Long>
        implements CityService {

    private final CityRepository repository;
    private final Mapper<City, CityDto, Long> mapper;

    @Autowired
    public CityServiceImpl(CityRepository repository,
                            Mapper<City, CityDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<City, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<City, CityDto, Long> getMapper() {
        return mapper;
    }

}
