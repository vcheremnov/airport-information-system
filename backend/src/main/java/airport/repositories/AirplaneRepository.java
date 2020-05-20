package airport.repositories;

import airport.entities.Airplane;
import airport.entities.AirplaneType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Collection;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    @Query(
        "select distinct a " +
        "from Airplane a " +
        "join a.repairs r " +
        "join a.techInspections t " +
        "where (:airplaneTypeId is null or a.airplaneType.id = :airplaneTypeId)" +
        "and (coalesce(:minCommDate, :minCommDate) is null or a.commissioningDate >= :minCommDate)" +
        "and (coalesce(:maxCommDate, :maxCommDate) is null or a.commissioningDate <= :maxCommDate)" +
        "and (coalesce(:minRepairDate, :minRepairDate) is null or r.startTime >= :minRepairDate)" +
        "and (coalesce(:maxRepairDate, :maxRepairDate) is null or r.startTime <= :maxRepairDate)" +
        "and (coalesce(:minInspDate, :minInspDate) is null or t.inspectionTime >= :minInspDate)" +
        "and (coalesce(:maxInspDate, :maxInspDate) is null or t.inspectionTime <= :maxInspDate)" +
        "and (:minRepairsNumber is null or :minRepairsNumber <= size(r))" +
        "and (:maxRepairsNumber is null or :maxRepairsNumber >= size(r))" +
        "and (:minFlightsNumber is null or :minFlightsNumber <= " +
                "(" +
                "   select count(f) from Flight f " +
                "   where f.airplane.id = a.id " +
                "   and f.isCancelled = false " +
                "   and current_timestamp >= f.flightTime" +
                ")" +
            ")" +
        "and (:maxFlightsNumber is null or :maxFlightsNumber >= " +
                "(" +
                "   select count(f) from Flight f " +
                "   where f.airplane.id = a.id " +
                "   and f.isCancelled = false " +
                "   and current_timestamp >= f.flightTime" +
                ")" +
            ")"
    ) Page<Airplane> searchByFilter(
            @Param("airplaneTypeId") Long airplaneTypeId,
            @Param("minCommDate") Date minCommissioningDate,
            @Param("maxCommDate") Date maxCommissioningDate,
            @Param("minRepairsNumber") Integer minRepairsNumber,
            @Param("maxRepairsNumber") Integer maxRepairsNumber,
            @Param("minRepairDate") Date minRepairDate,
            @Param("maxRepairDate") Date maxRepairDate,
            @Param("minInspDate") Date minTechInspectionDate,
            @Param("maxInspDate") Date maxTechInspectionDate,
            @Param("minFlightsNumber") Long minFlightsNumber,
            @Param("maxFlightsNumber") Long maxFlightsNumber,
            Pageable pageable
    );

//    @Query(
//        nativeQuery = true, value =
//        "select * " +
//        "from airplane " +
//        "where airplane.commissioning_date <= :time " +
//        "and 'ARRIVAL' = (" +
//        "   select f.type " +
//        "   from flight f " +
//        "   inner join city c on f.city_id = c.id " +
//        "   inner join airplane a on f.airplane_id = a.id" +
//        "   inner join airplane_type t on a.airplane_type_id = t.id" +
//        "   where f.airplane_id = a.id " +
//        "   and f.is_cancelled = false " +
//        "   and (f.flight_time + (3600. * c.distance / t.speed)::integer * interval '1 second') <= :time " +
//        "   order by f.flight_time desc" +
//        "   limit 1" +
//        ")"
//    )
//    Page<Airplane> getAirplanesAtTheAirport(
//        @Param("time") Date time,
//        Pageable pageable
//    );

}
