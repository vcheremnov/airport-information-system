package airport.mappers.impl;

import airport.dtos.AirplaneTypeDto;
import airport.entities.AirplaneType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AirplaneTypeMapper extends AbstractMapper<AirplaneType, AirplaneTypeDto, Long> {

    @Autowired
    public AirplaneTypeMapper(ModelMapper mapper) {
        super(mapper, AirplaneType.class, AirplaneTypeDto.class);
    }

}
