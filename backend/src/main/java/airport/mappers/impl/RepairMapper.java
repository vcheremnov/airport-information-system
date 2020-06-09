package airport.mappers.impl;

import airport.dtos.AirplaneDto;
import airport.dtos.RepairDto;
import airport.entities.Airplane;
import airport.entities.Repair;
import airport.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RepairMapper extends AbstractMapper<Repair, RepairDto, Long> {

    private final JpaRepository<Airplane, Long> airplaneRepository;
    private final Mapper<Airplane, AirplaneDto, Long> airplaneMapper;

    @Autowired
    public RepairMapper(ModelMapper mapper,
                        JpaRepository<Airplane, Long> airplaneRepository,
                        Mapper<Airplane, AirplaneDto, Long> airplaneMapper) {
        super(mapper, Repair.class, RepairDto.class);
        this.airplaneRepository = airplaneRepository;
        this.airplaneMapper = airplaneMapper;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(RepairDto::setAirplane);

        skipEntityField(Repair::setAirplane);
    }

    @Override
    protected void mapSpecificFields(Repair sourceEntity, RepairDto destinationDto) {
        destinationDto.setAirplane(airplaneMapper.toDto(sourceEntity.getAirplane()));
    }

    @Override
    protected void mapSpecificFields(RepairDto sourceDto, Repair destinationEntity) {
        destinationEntity.setAirplane(
                airplaneRepository.getOne(sourceDto.getAirplane().getId())
        );
    }
}
