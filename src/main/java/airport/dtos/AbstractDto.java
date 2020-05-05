package airport.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
public class AbstractDto<ID extends Serializable> implements Serializable {

    private ID id;

}
