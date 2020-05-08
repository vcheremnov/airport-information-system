package airport.mappers.impl;

import airport.dtos.CityDto;
import airport.dtos.FlightDelayDto;
import airport.dtos.FlightDto;
import airport.entities.*;
import airport.entities.types.TicketStatus;
import airport.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FlightMapper extends AbstractMapper<Flight, FlightDto, Long> {

    private final Mapper<FlightDelay, FlightDelayDto, Long> flightDelayMapper;
    private final Mapper<City, CityDto, Long> cityMapper;

    private final JpaRepository<Airplane, Long> airplaneRepository;

    @Autowired
    public FlightMapper(ModelMapper mapper,
                        Mapper<FlightDelay, FlightDelayDto, Long> flightDelayMapper,
                        Mapper<City, CityDto, Long> cityMapper,
                        JpaRepository<Airplane, Long> airplaneRepository) {
        super(mapper, Flight.class, FlightDto.class);
        this.flightDelayMapper = flightDelayMapper;
        this.cityMapper = cityMapper;
        this.airplaneRepository = airplaneRepository;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(FlightDto::setAirplaneId);
        skipDtoField(FlightDto::setCity);
        skipDtoField(FlightDto::setFlightDelay);

        skipEntityField(Flight::setAirplane);
        skipEntityField(Flight::setCity);
        skipEntityField(Flight::setFlightDelay);
    }

    @Override
    protected void mapSpecificFields(Flight sourceEntity, FlightDto destinationDto) {
        destinationDto.setAirplaneId(sourceEntity.getAirplane().getId());
        destinationDto.setCity(cityMapper.toDto(sourceEntity.getCity()));
        destinationDto.setFlightDelay(flightDelayMapper.toDto(sourceEntity.getFlightDelay()));

        List<Ticket> tickets = sourceEntity.getTickets();
        Map<TicketStatus, Long> statusToCount = tickets.stream()
                .collect(Collectors.groupingBy(Ticket::getStatus, Collectors.counting()));

        destinationDto.setTicketsSold(statusToCount.get(TicketStatus.SOLD));
        destinationDto.setTicketsBooked(statusToCount.get(TicketStatus.BOOKED));
        destinationDto.setTicketsReturned(statusToCount.get(TicketStatus.RETURNED));
    }

    @Override
    protected void mapSpecificFields(FlightDto sourceDto, Flight destinationEntity) {
        destinationEntity.setAirplane(airplaneRepository.getOne(sourceDto.getAirplaneId()));
        destinationEntity.setCity(cityMapper.toEntity(sourceDto.getCity()));
        destinationEntity.setFlightDelay(flightDelayMapper.toEntity(sourceDto.getFlightDelay()));
    }
}
