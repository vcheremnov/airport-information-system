package airport.repositories;

import airport.entities.FlightDelay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightDelayRepository extends JpaRepository<FlightDelay, Long> { }
