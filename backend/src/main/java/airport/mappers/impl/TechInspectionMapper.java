package airport.mappers.impl;

import airport.dtos.AirplaneDto;
import airport.dtos.TechInspectionDto;
import airport.entities.Airplane;
import airport.entities.TechInspection;
import airport.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TechInspectionMapper extends AbstractMapper<TechInspection, TechInspectionDto, Long> {

    private final JpaRepository<Airplane, Long> airplaneRepository;
    private final Mapper<Airplane, AirplaneDto, Long> airplaneMapper;

    @Autowired
    public TechInspectionMapper(ModelMapper mapper,
                                JpaRepository<Airplane, Long> airplaneRepository,
                                Mapper<Airplane, AirplaneDto, Long> airplaneMapper) {
        super(mapper, TechInspection.class, TechInspectionDto.class);
        this.airplaneRepository = airplaneRepository;
        this.airplaneMapper = airplaneMapper;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(TechInspectionDto::setAirplane);

        skipEntityField(TechInspection::setAirplane);
    }

    @Override
    protected void mapSpecificFields(TechInspection sourceEntity, TechInspectionDto destinationDto) {
        destinationDto.setAirplane(airplaneMapper.toDto(sourceEntity.getAirplane()));
    }

    @Override
    protected void mapSpecificFields(TechInspectionDto sourceDto, TechInspection destinationEntity) {
        destinationEntity.setAirplane(
                airplaneRepository.getOne(sourceDto.getAirplane().getId())
        );
    }

}
