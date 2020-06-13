package airport.services.impl;

import airport.dtos.FlightDto;
import airport.dtos.PassengerDto;
import airport.dtos.TicketDto;
import airport.dtos.parameters.FlightDelayInfo;
import airport.entities.Flight;
import airport.entities.FlightDelay;
import airport.entities.Passenger;
import airport.entities.Ticket;
import airport.entities.types.TicketStatus;
import airport.filters.FlightFilter;
import airport.mappers.Mapper;
import airport.repositories.FlightRepository;
import airport.repositories.PassengerRepository;
import airport.repositories.TicketRepository;
import airport.services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlightServiceImpl
        extends AbstractService<Flight, FlightDto, Long>
        implements FlightService {

    private final FlightRepository repository;
    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;
    private final Mapper<Flight, FlightDto, Long> mapper;
    private final Mapper<Passenger, PassengerDto, Long> passengerMapper;
    private final Mapper<Ticket, TicketDto, Long> ticketMapper;

    @Autowired
    public FlightServiceImpl(FlightRepository repository,
                             PassengerRepository passengerRepository,
                             TicketRepository ticketRepository,
                             Mapper<Flight, FlightDto, Long> mapper,
                             Mapper<Passenger, PassengerDto, Long> passengerMapper,
                             Mapper<Ticket, TicketDto, Long> ticketMapper) {
        this.repository = repository;
        this.passengerRepository = passengerRepository;
        this.ticketRepository = ticketRepository;
        this.mapper = mapper;
        this.passengerMapper = passengerMapper;
        this.ticketMapper = ticketMapper;
    }

    @Override
    @Transactional
    public FlightDto save(Long flightId, FlightDto flightDto) {
        Flight flight = getEntityByIdOrThrow(flightId);
        Boolean beforeWasCancelled = flight.getIsCancelled();
        Boolean nowIsCancelled = flightDto.getIsCancelled();

        if (beforeWasCancelled && !nowIsCancelled) {
            throw new RuntimeException("Flight has been already cancelled");
        }

        if (!beforeWasCancelled && nowIsCancelled &&
                System.currentTimeMillis() > flight.getFlightTime().getTime()) {
            throw new RuntimeException("Flight has already taken place");
        }

        flightDto.setId(flightId);
        flight = mapper.toEntity(flightDto);
        flight = repository.save(flight);
        return mapper.toDto(flight);
    }

    @Override
    public Page<FlightDto> search(FlightFilter filter, Pageable pageable) {
        return repository.searchByFilter(
                filter.getAirplaneId(),
                filter.getFlightType(),
                filter.getAirplaneTypeId(),
                filter.getCityId(),
                filter.getIsCancelled(),
                filter.getIsDelayed(),
                filter.getDelayReason(),
                filter.getMinDate(),
                filter.getMaxDate(),
                filter.getMinDuration(),
                filter.getMaxDuration(),
                filter.getMinTicketPrice(),
                filter.getMaxTicketPrice(),
                pageable
        ).map(mapper::toDto);
    }

    @Override
    @Transactional
    public FlightDto delayFlight(Long flightId, FlightDelayInfo flightDelayInfo) {
        Flight flight = getEntityByIdOrThrow(flightId);

        if (flight.getIsCancelled()) {
            throw new RuntimeException("Flight has been cancelled");
        }

        if (System.currentTimeMillis() > flight.getFlightTime().getTime()) {
            throw new RuntimeException("Flight has already taken place");
        }

        if (flightDelayInfo.getNewFlightTime().before(flight.getFlightTime())) {
            throw new RuntimeException("New flight time can't be less than the previous one");
        }

        flight.setFlightTime(flightDelayInfo.getNewFlightTime());

        FlightDelay flightDelay = new FlightDelay();
        flightDelay.setId(flightId);
        flightDelay.setFlight(flight);
        flightDelay.setDelayReason(flightDelayInfo.getDelayReason());
        flight.setFlightDelay(flightDelay);

        flight = repository.save(flight);
        return mapper.toDto(flight);
    }

    @Override
    @Transactional
    public TicketDto addTicket(Long flightId, TicketDto ticketDto) {
        Flight flight = getEntityByIdOrThrow(flightId);

        if (flight.getIsCancelled()) {
            throw new RuntimeException("Flight has been cancelled");
        }

        if (System.currentTimeMillis() > flight.getFlightTime().getTime()) {
            throw new RuntimeException("Flight has already taken place");
        }

        int totalSeatsNumber = flight.getAirplane().getAirplaneType().getCapacity();
        long occupiedSeatsNumber = flight.getTickets()
                .stream()
                .filter(t -> t.getStatus() != TicketStatus.RETURNED)
                .count();
        if (occupiedSeatsNumber >= totalSeatsNumber) {
            throw new RuntimeException("No seats available");
        }

        Passenger passenger = passengerMapper.toEntity(ticketDto.getPassenger());
        passenger = passengerRepository.save(passenger);
        PassengerDto passengerDto = passengerMapper.toDto(passenger);
        ticketDto.setPassenger(passengerDto);
        ticketDto.setFlightId(flightId);

        Ticket ticket = ticketMapper.toEntity(ticketDto);
        ticket = ticketRepository.save(ticket);
        return ticketMapper.toDto(ticket);
    }

    @Override
    public FlightDto create(FlightDto flightDto) {
        flightDto.setIsCancelled(false);
        return super.create(flightDto);
    }

    @Override
    public Page<TicketDto> getTickets(Long flightId, Pageable pageable) {
        return ticketRepository
                .getAllByFlightId(flightId, pageable)
                .map(ticketMapper::toDto);
    }

    @Override
    protected JpaRepository<Flight, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Flight, FlightDto, Long> getMapper() {
        return mapper;
    }
}
