package airport.repositories;

import airport.entities.Employee;
import airport.entities.types.Sex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(
        "select distinct e " +
        "from Employee e " +
        "join e.team t " +
        "where (:sex is null or e.sex = :sex)" +
        "and (:departmentId is null or e.team.department.id = :departmentId)" +
        "and (coalesce(:minBirthDate, :minBirthDate) is null or e.birthDate >= :minBirthDate)" +
        "and (coalesce(:maxBirthDate, :maxBirthDate) is null or e.birthDate <= :maxBirthDate)" +
        "and (coalesce(:minEmploymentDate, :minEmploymentDate) is null or e.employmentDate >= :minEmploymentDate)" +
        "and (coalesce(:maxEmploymentDate, :maxEmploymentDate) is null or e.employmentDate >= :maxEmploymentDate)" +
        "and (:minSalary is null or e.salary >= :minSalary)" +
        "and (:maxSalary is null or e.salary <= :maxSalary)" +
        "and (:minTeamAverageSalary is null or :minTeamAverageSalary <= " +
                "(select avg(e.salary) from Employee e join e.team et group by et having et.id = t.id)" +
            ")" +
        "and (:maxTeamAverageSalary is null or :maxTeamAverageSalary >= " +
                "(select avg(e.salary) from Employee e join e.team et group by et having et.id = t.id)" +
            ")"
    )
    Page<Employee> searchByFilter(
            @Param("sex") Sex sex,
            @Param("departmentId") Long departmentId,
            @Param("minBirthDate") Date minBirthDate,
            @Param("maxBirthDate") Date maxBirthDate,
            @Param("minEmploymentDate") Date minEmploymentDate,
            @Param("maxEmploymentDate") Date maxEmploymentDate,
            @Param("minSalary") Integer minSalary,
            @Param("maxSalary") Integer maxSalary,
            @Param("minTeamAverageSalary") Double minTeamAverageSalary,
            @Param("maxTeamAverageSalary") Double maxTeamAverageSalary,
            Pageable pageable
    );

    @Query(
        "select distinct e from Employee e join e.medicalExaminations m " +
        "where function('year', m.examDate) = :year " +
        "and m.isPassed = :isPassed"
    )
    Page<Employee> findEmployeesByMedExamResult(
            @Param("year") Integer year,
            @Param("isPassed") Boolean isPassed,
            Pageable pageable
    );

    Page<Employee> getAllByTeamId(Long teamId, Pageable pageable);

}
