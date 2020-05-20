package airport.mappers.impl;

import airport.dtos.PassengerDto;
import airport.entities.Passenger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PassengerMapper extends AbstractMapper<Passenger, PassengerDto, Long> {

    @Autowired
    public PassengerMapper(ModelMapper mapper) {
        super(mapper, Passenger.class, PassengerDto.class);
    }

}
