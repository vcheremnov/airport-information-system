package airport.mappers.impl;

import airport.dtos.RepairDto;
import airport.entities.Airplane;
import airport.entities.Repair;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RepairMapper extends AbstractMapper<Repair, RepairDto, Long> {

    private final JpaRepository<Airplane, Long> airplaneRepository;

    @Autowired
    public RepairMapper(ModelMapper mapper,
                        JpaRepository<Airplane, Long> airplaneRepository) {
        super(mapper, Repair.class, RepairDto.class);
        this.airplaneRepository = airplaneRepository;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(RepairDto::setAirplaneId);

        skipEntityField(Repair::setAirplane);
    }

    @Override
    protected void mapSpecificFields(Repair sourceEntity, RepairDto destinationDto) {
        destinationDto.setAirplaneId(sourceEntity.getAirplane().getId());
    }

    @Override
    protected void mapSpecificFields(RepairDto sourceDto, Repair destinationEntity) {
        destinationEntity.setAirplane(airplaneRepository.getOne(sourceDto.getAirplaneId()));
    }
}
