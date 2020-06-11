package airport.mappers.impl;

import airport.dtos.CityDto;
import airport.dtos.FlightDelayDto;
import airport.dtos.FlightDto;
import airport.entities.*;
import airport.entities.types.TicketStatus;
import airport.mappers.Mapper;
import airport.repositories.CityRepository;
import airport.repositories.FlightDelayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FlightMapper extends AbstractMapper<Flight, FlightDto, Long> {

    private final Mapper<FlightDelay, FlightDelayDto, Long> flightDelayMapper;
    private final Mapper<City, CityDto, Long> cityMapper;

    private final JpaRepository<Airplane, Long> airplaneRepository;
    private final FlightDelayRepository flightDelayRepository;
    private final CityRepository cityRepository;

    @Autowired
    public FlightMapper(ModelMapper mapper,
                        Mapper<FlightDelay, FlightDelayDto, Long> flightDelayMapper,
                        Mapper<City, CityDto, Long> cityMapper,
                        JpaRepository<Airplane, Long> airplaneRepository,
                        FlightDelayRepository flightDelayRepository,
                        CityRepository cityRepository) {
        super(mapper, Flight.class, FlightDto.class);
        this.flightDelayMapper = flightDelayMapper;
        this.cityMapper = cityMapper;
        this.airplaneRepository = airplaneRepository;
        this.flightDelayRepository = flightDelayRepository;
        this.cityRepository = cityRepository;
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

        destinationDto.setTicketsSold(statusToCount.getOrDefault(TicketStatus.SOLD, 0L));
        destinationDto.setTicketsBooked(statusToCount.getOrDefault(TicketStatus.BOOKED, 0L));
        destinationDto.setTicketsReturned(statusToCount.getOrDefault(TicketStatus.RETURNED, 0L));
    }

    @Override
    protected void mapSpecificFields(FlightDto sourceDto, Flight destinationEntity) {
        destinationEntity.setAirplane(
                getEntityByIdOrThrow(airplaneRepository, sourceDto.getAirplaneId())
        );
        destinationEntity.setCity(
                getEntityByIdOrThrow(cityRepository, sourceDto.getCity().getId())
        );

        if (sourceDto.getFlightDelay() != null) {
            destinationEntity.setFlightDelay(
                    getEntityByIdOrThrow(flightDelayRepository, sourceDto.getFlightDelay().getId())
            );
        }
    }
}
