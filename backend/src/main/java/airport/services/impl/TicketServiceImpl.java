package airport.services.impl;

import airport.dtos.TicketDto;
import airport.entities.Ticket;
import airport.filters.TicketFilter;
import airport.mappers.Mapper;
import airport.repositories.TicketRepository;
import airport.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TicketServiceImpl
        extends AbstractService<Ticket, TicketDto, Long>
        implements TicketService {

    private final TicketRepository repository;
    private final Mapper<Ticket, TicketDto, Long> mapper;

    @Autowired
    public TicketServiceImpl(TicketRepository repository,
                           Mapper<Ticket, TicketDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<Ticket, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Ticket, TicketDto, Long> getMapper() {
        return mapper;
    }

    @Override
    public Page<TicketDto> search(TicketFilter filter, Pageable pageable) {
        return repository.searchByFilter(
                filter.getFlightId(),
                filter.getMinFlightDate(),
                filter.getMaxFlightDate(),
                filter.getMinPrice(),
                filter.getMaxPrice(),
                filter.getTicketStatus(),
                filter.getPassengerSex(),
                filter.getMinPassengerBirthDate(),
                filter.getMaxPassengerBirthDate(),
                pageable
        ).map(mapper::toDto);
    }

    @Override
    public Double getAverageTicketsSoldByCity(Long cityId) {
        return Objects.requireNonNullElse(
                repository.getAverageTicketsSoldByCityId(cityId), 0.0
        );
    }
}
