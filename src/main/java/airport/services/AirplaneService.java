package airport.services;

import airport.dtos.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.Collection;

public interface AirplaneService extends Service<AirplaneDto, Long> {
    Page<RepairDto> getRepairs(Long airplaneId, Pageable pageable);

    Page<TechInspectionDto> getTechInspections(Long airplaneId, Pageable pageable);

    Page<AirplaneDto> getInspectedBetween(Date minDate, Date maxDate, Pageable pageable);

    Page<AirplaneDto> getRepairedBetween(Date minDate, Date maxDate, Pageable pageable);
}

