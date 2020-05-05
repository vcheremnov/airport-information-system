package airport.mappers.impl;

import airport.dtos.ChiefDto;
import airport.entities.AbstractEntity;
import airport.entities.Chief;
import airport.entities.Department;
import airport.entities.MedicalExamination;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ChiefMapper extends AbstractMapper<Chief, ChiefDto, Long> {

    @Autowired
    public ChiefMapper(ModelMapper mapper) {
        super(mapper, Chief.class, ChiefDto.class);
    }

}
