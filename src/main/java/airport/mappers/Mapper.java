package airport.mappers;

import airport.dtos.AbstractDto;
import airport.entities.AbstractEntity;

import java.io.Serializable;

public interface Mapper
        <E extends AbstractEntity<ID>,
        DTO extends AbstractDto<ID>,
        ID extends Serializable> {

    E toEntity(DTO dto);

    DTO toDto(E entity);

}