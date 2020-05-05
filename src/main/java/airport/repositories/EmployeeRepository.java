package airport.repositories;

import airport.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EmployeeRepository extends
        JpaRepository<Employee, Long>,
        JpaSpecificationExecutor<Employee> {

    @Query("select distinct e from Employee e join e.medicalExaminations m " +
            "where function('year', m.examDate) = :year " +
            "and m.isPassed = :isPassed")
    Page<Employee> findEmployeesByMedExamResult(
            Pageable pageable,
            Integer year,
            @Param("isPassed") Boolean isPassed);


}
