package airport.repositories;

import airport.entities.MedicalExamination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MedicalExaminationRepository extends
        JpaRepository<MedicalExamination, Long>,
        JpaSpecificationExecutor<MedicalExamination> { }
