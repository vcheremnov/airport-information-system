package airport.services.impl;

import airport.dtos.TechInspectionDto;
import airport.dtos.TechInspectionDto;
import airport.entities.TechInspection;
import airport.entities.TechInspection;
import airport.mappers.Mapper;
import airport.repositories.TechInspectionRepository;
import airport.services.TechInspectionService;
import airport.services.TechInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TechInspectionServiceImpl
        extends AbstractService<TechInspection, TechInspectionDto, Long>
        implements TechInspectionService {

    private final TechInspectionRepository repository;
    private final Mapper<TechInspection, TechInspectionDto, Long> mapper;

    @Autowired
    public TechInspectionServiceImpl(TechInspectionRepository repository,
                           Mapper<TechInspection, TechInspectionDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<TechInspection, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<TechInspection, TechInspectionDto, Long> getMapper() {
        return mapper;
    }

}
