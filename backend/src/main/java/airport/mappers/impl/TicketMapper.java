package airport.mappers.impl;

import airport.dtos.PassengerDto;
import airport.dtos.TicketDto;
import airport.entities.Flight;
import airport.entities.Passenger;
import airport.entities.Ticket;
import airport.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TicketMapper extends AbstractMapper<Ticket, TicketDto, Long> {

    private final JpaRepository<Flight, Long> flightRepository;
    private final JpaRepository<Passenger, Long> passengerRepository;
    private final Mapper<Passenger, PassengerDto, Long> passengerMapper;

    @Autowired
    public TicketMapper(ModelMapper mapper,
                        JpaRepository<Flight, Long> flightRepository,
                        JpaRepository<Passenger, Long> passengerRepository,
                        Mapper<Passenger, PassengerDto, Long> passengerMapper) {
        super(mapper, Ticket.class, TicketDto.class);
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(TicketDto::setFlightId);
        skipDtoField(TicketDto::setPassenger);

        skipEntityField(Ticket::setFlight);
        skipEntityField(Ticket::setPassenger);
    }

    @Override
    protected void mapSpecificFields(Ticket sourceEntity, TicketDto destinationDto) {
        destinationDto.setFlightId(sourceEntity.getFlight().getId());
        destinationDto.setPassenger(passengerMapper.toDto(sourceEntity.getPassenger()));
        destinationDto.setPrice(sourceEntity.getFlight().getTicketPrice());
    }

    @Override
    protected void mapSpecificFields(TicketDto sourceDto, Ticket destinationEntity) {
        destinationEntity.setFlight(flightRepository.getOne(sourceDto.getFlightId()));
        destinationEntity.setPassenger(passengerRepository.getOne(sourceDto.getPassenger().getId()));
    }
}
