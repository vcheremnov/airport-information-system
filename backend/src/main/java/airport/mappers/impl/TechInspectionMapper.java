package airport.mappers.impl;

import airport.dtos.TechInspectionDto;
import airport.entities.Airplane;
import airport.entities.TechInspection;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TechInspectionMapper extends AbstractMapper<TechInspection, TechInspectionDto, Long> {

    private final JpaRepository<Airplane, Long> airplaneRepository;

    @Autowired
    public TechInspectionMapper(ModelMapper mapper,
                                JpaRepository<Airplane, Long> airplaneRepository) {
        super(mapper, TechInspection.class, TechInspectionDto.class);
        this.airplaneRepository = airplaneRepository;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(TechInspectionDto::setAirplaneId);

        skipEntityField(TechInspection::setAirplane);
    }

    @Override
    protected void mapSpecificFields(TechInspection sourceEntity, TechInspectionDto destinationDto) {
        destinationDto.setAirplaneId(sourceEntity.getAirplane().getId());
    }

    @Override
    protected void mapSpecificFields(TechInspectionDto sourceDto, TechInspection destinationEntity) {
        destinationEntity.setAirplane(airplaneRepository.getOne(sourceDto.getAirplaneId()));
    }

}
