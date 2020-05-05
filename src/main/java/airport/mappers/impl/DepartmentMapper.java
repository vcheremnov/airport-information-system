package airport.mappers.impl;

import airport.dtos.ChiefDto;
import airport.dtos.DepartmentDto;
import airport.entities.*;
import airport.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DepartmentMapper extends AbstractMapper<Department, DepartmentDto, Long> {

    private final Mapper<Chief, ChiefDto, Long> chiefMapper;

    @Autowired
    public DepartmentMapper(ModelMapper mapper,
                            Mapper<Chief, ChiefDto, Long> chiefMapper) {
        super(mapper, Department.class, DepartmentDto.class);
        this.chiefMapper = chiefMapper;
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
        destinationEntity.setChief(chiefMapper.toEntity(sourceDto.getChief()));
    }
}
