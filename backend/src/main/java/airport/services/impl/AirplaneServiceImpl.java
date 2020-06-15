package airport.services.impl;

import airport.dtos.AirplaneDto;
import airport.dtos.FlightDelayDto;
import airport.dtos.RepairDto;
import airport.dtos.TechInspectionDto;
import airport.entities.Airplane;
import airport.entities.Repair;
import airport.entities.TechInspection;
import airport.filters.AirplaneFilter;
import airport.mappers.Mapper;
import airport.repositories.AirplaneRepository;
import airport.repositories.RepairRepository;
import airport.repositories.TechInspectionRepository;
import airport.services.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class AirplaneServiceImpl
        extends AbstractService<Airplane, AirplaneDto, Long>
        implements AirplaneService {

    private final AirplaneRepository repository;
    private final TechInspectionRepository techInspectionRepository;
    private final RepairRepository repairRepository;

    private final Mapper<Airplane, AirplaneDto, Long> mapper;
    private final Mapper<TechInspection, TechInspectionDto, Long> techInspectionMapper;
    private final Mapper<Repair, RepairDto, Long> repairMapper;

    @Autowired
    public AirplaneServiceImpl(AirplaneRepository repository,
                               TechInspectionRepository techInspectionRepository,
                               RepairRepository repairRepository,
                               Mapper<Airplane, AirplaneDto, Long> mapper,
                               Mapper<TechInspection, TechInspectionDto, Long> techInspectionMapper,
                               Mapper<Repair, RepairDto, Long> repairMapper) {
        this.repository = repository;
        this.techInspectionRepository = techInspectionRepository;
        this.repairRepository = repairRepository;
        this.mapper = mapper;
        this.techInspectionMapper = techInspectionMapper;
        this.repairMapper = repairMapper;
    }

    @Override
    public AirplaneDto create(AirplaneDto airplaneDto) {
        airplaneDto.setCommissioningDate(new Date(System.currentTimeMillis()));
        return super.create(airplaneDto);
    }

    @Override
    public Page<RepairDto> getRepairs(Long airplaneId, Pageable pageable) {
        return repairRepository
                .findAllByAirplaneId(airplaneId, pageable)
                .map(repairMapper::toDto);
    }

    @Override
    public Page<TechInspectionDto> getTechInspections(Long airplaneId, Pageable pageable) {
        return techInspectionRepository
                .findAllByAirplaneId(airplaneId, pageable)
                .map(techInspectionMapper::toDto);
    }

    @Override
    public Page<AirplaneDto> search(AirplaneFilter filter, Pageable pageable) {
        return repository
                .searchByFilter(
                        prepareStringToLikeStatement(filter.getAirplaneTypeName()),
                        filter.getMinCommissioningDate(),
                        filter.getMaxCommissioningDate(),
                        filter.getMinRepairsNumber(),
                        filter.getMaxRepairsNumber(),
                        filter.getMinRepairDate(),
                        filter.getMaxRepairDate(),
                        filter.getMinTechInspectionDate(),
                        filter.getMaxTechInspectionDate(),
                        pageable
                ).map(mapper::toDto);
    }

//    @Override
//    public Page<AirplaneDto> getAirplanesAtTheAirport(Date time, Pageable pageable) {
//        return repository
//                .getAirplanesAtTheAirport(time, pageable)
//                .map(mapper::toDto);
//    }

    @Override
    protected JpaRepository<Airplane, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Airplane, AirplaneDto, Long> getMapper() {
        return mapper;
    }
}
