package airport.repositories;

import airport.entities.Flight;
import airport.entities.Ticket;
import airport.entities.types.FlightDelayReason;
import airport.entities.types.FlightType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query(
        "select distinct f " +
        "from Flight f " +
        "join f.airplane a " +
        "join a.airplaneType atype " +
        "join f.city c " +
        "left join f.flightDelay as fd " +
        "where (:airplaneId is null or a.id = :airplaneId) " +
        "and (:flightType is null or f.flightType = :flightType) " +
        "and (:airplaneTypeName is null or lower(atype.name) like :airplaneTypeName) " +
        "and (:cityName is null or lower(c.name) like :cityName) " +
        "and (coalesce(:minDate, :minDate) is null or f.flightTime >= :minDate) " +
        "and (coalesce(:maxDate, :maxDate) is null or f.flightTime <= :maxDate) " +
        "and (:isCancelled is null or f.isCancelled = :isCancelled) " +
        "and (:minDuration is null or f.duration >= :minDuration) " +
        "and (:maxDuration is null or f.duration <= :maxDuration) " +
        "and (:minTicketPrice is null or f.ticketPrice >= :minTicketPrice) " +
        "and (:maxTicketPrice is null or f.ticketPrice <= :maxTicketPrice) " +
        "and (:isDelayed is null or :isDelayed = case when fd.id is null then false else true end) " +
        "and (:delayReason is null or fd.delayReason = :delayReason)"
    )
    Page<Flight> searchByFilter(
            @Param("airplaneId") Long airplaneId,
            @Param("flightType") FlightType flightType,
            @Param("airplaneTypeName") String airplaneTypeName,
            @Param("cityName") String cityName,
            @Param("isCancelled") Boolean isCancelled,
            @Param("isDelayed") Boolean isDelayed,
            @Param("delayReason") FlightDelayReason delayReason,
            @Param("minDate") Date minDate,
            @Param("maxDate") Date maxDate,
            @Param("minDuration") Double minDuration,
            @Param("maxDuration") Double maxDuration,
            @Param("minTicketPrice") Double minTicketPrice,
            @Param("maxTicketPrice") Double maxTicketPrice,
            Pageable pageable
    );

}
