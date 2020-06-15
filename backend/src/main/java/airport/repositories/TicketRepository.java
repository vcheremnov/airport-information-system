package airport.repositories;

import airport.entities.Ticket;
import airport.entities.types.Sex;
import airport.entities.types.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TicketRepository
        extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    @Query(
        "select distinct t " +
        "from Ticket t " +
        "join t.flight f " +
        "join f.airplane a " +
        "join a.airplaneType atype " +
        "join f.city c " +
        "join t.passenger p " +
        "where (:flightId is null or f.id = :flightId)" +
        "and (coalesce(:minFlightDate, :minFlightDate) is null or f.flightTime >= :minFlightDate)" +
        "and (coalesce(:maxFlightDate, :maxFlightDate) is null or f.flightTime <= :maxFlightDate) " +
        "and (:ticketStatus is null or t.status = :ticketStatus)" +
        "and (:minPrice is null or f.ticketPrice >= :minPrice)" +
        "and (:maxPrice is null or f.ticketPrice <= :maxPrice)" +
        "and (:sex is null or p.sex = :sex) " +
        "and (coalesce(:minBirthDate, :minBirthDate) is null or p.birthDate >= :minBirthDate) " +
        "and (coalesce(:maxBirthDate, :maxBirthDate) is null or p.birthDate <= :maxBirthDate) "
    )
    Page<Ticket> searchByFilter(
        @Param("flightId") Long flightId,
        @Param("minFlightDate") Date minFlightDate,
        @Param("maxFlightDate") Date maxFlightDate,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("ticketStatus") TicketStatus ticketStatus,
        @Param("sex") Sex passengerSex,
        @Param("minBirthDate") Date minPassengerBirthDate,
        @Param("maxBirthDate") Date maxPassengerBirthDate,
        Pageable pageable
    );

    @Query(nativeQuery = true, value = "select get_average_tickets_sold_by_city_id(:cityId)")
    Double getAverageTicketsSoldByCityId(@Param ("cityId") Long cityId);

    Page<Ticket> getAllByFlightId(Long flightId, Pageable pageable);

}
