package airport.mappers.impl;

import airport.dtos.TicketDto;
import airport.entities.Flight;
import airport.entities.Passenger;
import airport.entities.Ticket;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TicketMapper extends AbstractMapper<Ticket, TicketDto, Long> {

    private final JpaRepository<Flight, Long> flightRepository;
    private final JpaRepository<Passenger, Long> passengerRepository;

    @Autowired
    public TicketMapper(ModelMapper mapper,
                        JpaRepository<Flight, Long> flightRepository,
                        JpaRepository<Passenger, Long> passengerRepository) {
        super(mapper, Ticket.class, TicketDto.class);
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(TicketDto::setFlightId);
        skipDtoField(TicketDto::setPassengerId);

        skipEntityField(Ticket::setFlight);
        skipEntityField(Ticket::setPassenger);
    }

    @Override
    protected void mapSpecificFields(Ticket sourceEntity, TicketDto destinationDto) {
        destinationDto.setFlightId(sourceEntity.getFlight().getId());
        destinationDto.setPassengerId(sourceEntity.getPassenger().getId());
    }

    @Override
    protected void mapSpecificFields(TicketDto sourceDto, Ticket destinationEntity) {
        destinationEntity.setFlight(flightRepository.getOne(sourceDto.getFlightId()));
        destinationEntity.setPassenger(passengerRepository.getOne(sourceDto.getPassengerId()));
    }
}
