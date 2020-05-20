package airport.mappers.impl;

import airport.dtos.FlightDelayDto;
import airport.entities.Flight;
import airport.entities.FlightDelay;
import airport.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FlightDelayMapper extends AbstractMapper<FlightDelay, FlightDelayDto, Long> {

    private final JpaRepository<Flight, Long> flightRepository;

    @Autowired
    public FlightDelayMapper(ModelMapper mapper,
                             JpaRepository<Flight, Long> flightRepository) {
        super(mapper, FlightDelay.class, FlightDelayDto.class);
        this.flightRepository = flightRepository;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(FlightDelayDto::setId);

        skipEntityField(FlightDelay::setFlight);
    }

    @Override
    protected void mapSpecificFields(FlightDelay sourceEntity, FlightDelayDto destinationDto) {
        destinationDto.setId(sourceEntity.getFlight().getId());
    }

    @Override
    protected void mapSpecificFields(FlightDelayDto sourceDto, FlightDelay destinationEntity) {
        destinationEntity.setFlight(flightRepository.getOne(sourceDto.getId()));
    }
}
