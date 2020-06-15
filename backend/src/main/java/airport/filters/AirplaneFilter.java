package airport.filters;

import airport.entities.AirplaneType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class AirplaneFilter {

    private String airplaneTypeName;

    private Date minCommissioningDate;
    private Date maxCommissioningDate;

    private Integer minRepairsNumber;
    private Integer maxRepairsNumber;

    private Date minRepairDate;
    private Date maxRepairDate;

    private Date minTechInspectionDate;
    private Date maxTechInspectionDate;

}
