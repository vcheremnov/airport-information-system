package airport.repositories;

import airport.entities.Chief;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiefRepository extends JpaRepository<Chief, Long> { }
