package airport.mappers.impl;

import airport.dtos.ChiefDto;
import airport.dtos.DepartmentDto;
import airport.entities.*;
import airport.mappers.Mapper;
import airport.repositories.ChiefRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DepartmentMapper extends AbstractMapper<Department, DepartmentDto, Long> {

    private final Mapper<Chief, ChiefDto, Long> chiefMapper;
    private final ChiefRepository chiefRepository;

    @Autowired
    public DepartmentMapper(ModelMapper mapper,
                            Mapper<Chief, ChiefDto, Long> chiefMapper,
                            ChiefRepository chiefRepository) {
        super(mapper, Department.class, DepartmentDto.class);
        this.chiefMapper = chiefMapper;
        this.chiefRepository = chiefRepository;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(DepartmentDto::setChief);

        skipEntityField(Department::setChief);
        skipEntityField(Department::setTeams);
    }

    @Override
    protected void mapSpecificFields(Department sourceEntity, DepartmentDto destinationDto) {
        destinationDto.setChief(chiefMapper.toDto(sourceEntity.getChief()));
    }

    @Override
    protected void mapSpecificFields(DepartmentDto sourceDto, Department destinationEntity) {
        destinationEntity.setChief(
                getEntityByIdOrThrow(chiefRepository, sourceDto.getChief().getId())
        );
    }
}
