package airport.repositories;

import airport.entities.Passenger;
import airport.entities.types.Sex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PassengerRepository
        extends JpaRepository<Passenger, Long>, JpaSpecificationExecutor<Passenger> {

    @Query(
        "select distinct p from Passenger p join p.tickets t join t.flight f " +
        "where (:flightId is null or f.id = :flightId)" +
        "and (coalesce(:minFlightTime, :minFlightTime) is null or f.flightTime >= :minFlightTime)" +
        "and (coalesce(:maxFlightTime, :maxFlightTime) is null or f.flightTime <= :maxFlightTime) " +
        "and (:sex is null or p.sex = :sex) " +
        "and (coalesce(:minBirthDate, :minBirthDate) is null or p.birthDate >= :minBirthDate) " +
        "and (coalesce(:maxBirthDate, :maxBirthDate) is null or p.birthDate <= :maxBirthDate) "
    )
    Page<Passenger> findAllByFilter(
            @Param("flightId") Long flightId,
            @Param("minFlightTime") Date minFlightTime,
            @Param("maxFlightTime") Date maxFlightTime,
            @Param("sex") Sex sex,
            @Param("minBirthDate") Date minBirthDate,
            @Param("maxBirthDate") Date maxBirthDate,
            Pageable pageable
    );

}
