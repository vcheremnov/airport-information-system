package airport.services;

import airport.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Collection;

public interface AirplaneService extends Service<AirplaneDto, Long> {
    Collection<RepairDto> getRepairs(Long airplaneId);

    Page<TechInspectionDto> getTechInspections(Pageable pageable, Long airplaneId);

    Collection<AirplaneDto> getInspectedBetween(Date minDate, Date maxDate);

    Collection<AirplaneDto> getRepairedBetween(Date minDate, Date maxDate);
}

