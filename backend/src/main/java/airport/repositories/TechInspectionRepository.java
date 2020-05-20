package airport.repositories;

import airport.entities.TechInspection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechInspectionRepository extends JpaRepository<TechInspection, Long> {
    Page<TechInspection> findAllByAirplaneId(Long airplaneId, Pageable pageable);
}
