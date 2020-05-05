package airport.services.impl;

import airport.dtos.AirplaneDto;
import airport.dtos.RepairDto;
import airport.dtos.TechInspectionDto;
import airport.entities.Airplane;
import airport.entities.Repair;
import airport.entities.TechInspection;
import airport.mappers.Mapper;
import airport.repositories.AirplaneRepository;
import airport.repositories.TechInspectionRepository;
import airport.services.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AirplaneServiceImpl
        extends AbstractService<Airplane, AirplaneDto, Long>
        implements AirplaneService {

    private final AirplaneRepository repository;
    private final TechInspectionRepository techInspectionRepository;

    private final Mapper<Airplane, AirplaneDto, Long> mapper;
    private final Mapper<TechInspection, TechInspectionDto, Long> techInspectionMapper;
    private final Mapper<Repair, RepairDto, Long> repairMapper;

    @Autowired
    public AirplaneServiceImpl(AirplaneRepository repository,
                               TechInspectionRepository techInspectionRepository, Mapper<Airplane, AirplaneDto, Long> mapper,
                               Mapper<TechInspection, TechInspectionDto, Long> techInspectionMapper,
                               Mapper<Repair, RepairDto, Long> repairMapper) {
        this.repository = repository;
        this.techInspectionRepository = techInspectionRepository;
        this.mapper = mapper;
        this.techInspectionMapper = techInspectionMapper;
        this.repairMapper = repairMapper;
    }

    @Override
    public Collection<RepairDto> getRepairs(Long airplaneId) {
        return getEntityByIdOrThrow(airplaneId)
                .getRepairs()
                .stream()
                .map(repairMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<TechInspectionDto> getTechInspections(Pageable pageable, Long airplaneId) {
        return techInspectionRepository
                .findAllByAirplaneId(airplaneId, pageable)
                .map(techInspectionMapper::toDto);
    }

    @Override
    public Collection<AirplaneDto> getInspectedBetween(Date minDate, Date maxDate) {
        return repository
                .findAllInspectedAirplanesBetween(minDate, maxDate)
                .stream()
                .map(getMapper()::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<AirplaneDto> getRepairedBetween(Date minDate, Date maxDate) {
        return repository
                .findAllRepairedAirplanesBetween(minDate, maxDate)
                .stream()
                .map(getMapper()::toDto)
                .collect(Collectors.toList());
    }

    @Override
    protected JpaRepository<Airplane, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Airplane, AirplaneDto, Long> getMapper() {
        return mapper;
    }
}
