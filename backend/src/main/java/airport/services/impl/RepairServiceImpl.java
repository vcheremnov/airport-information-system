package airport.services.impl;

import airport.dtos.RepairDto;
import airport.dtos.RepairDto;
import airport.entities.Repair;
import airport.entities.Repair;
import airport.mappers.Mapper;
import airport.repositories.RepairRepository;
import airport.services.RepairService;
import airport.services.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RepairServiceImpl
        extends AbstractService<Repair, RepairDto, Long>
        implements RepairService {

    private final RepairRepository repository;
    private final Mapper<Repair, RepairDto, Long> mapper;

    @Autowired
    public RepairServiceImpl(RepairRepository repository,
                           Mapper<Repair, RepairDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<Repair, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<Repair, RepairDto, Long> getMapper() {
        return mapper;
    }

}