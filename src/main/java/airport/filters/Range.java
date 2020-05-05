package airport.filters;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Range<T> {

    private T minValue;
    private T maxValue;

}
