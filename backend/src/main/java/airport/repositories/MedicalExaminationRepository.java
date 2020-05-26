package airport.repositories;

import airport.entities.MedicalExamination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MedicalExaminationRepository extends JpaRepository<MedicalExamination, Long> {

    Page<MedicalExamination> getAllByEmployeeId(Long employeeId, Pageable pageable);

}
