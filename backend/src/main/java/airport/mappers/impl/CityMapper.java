package airport.mappers.impl;

import airport.dtos.CityDto;
import airport.entities.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CityMapper extends AbstractMapper<City, CityDto, Long> {

    @Autowired
    public CityMapper(ModelMapper mapper) {
        super(mapper, City.class, CityDto.class);
    }

}
