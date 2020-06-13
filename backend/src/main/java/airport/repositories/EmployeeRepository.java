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
        "join e.medicalExaminations m " +
        "where (:sex is null or e.sex = :sex) " +
        "and (:name is null or lower(e.name) like :name) " +
        "and (:teamName is null or lower(e.team.name) like :teamName) " +
        "and (:departmentName is null or lower(e.team.department.name) like :departmentName) " +
        "and (coalesce(:minBirthDate, :minBirthDate) is null or e.birthDate >= :minBirthDate)" +
        "and (coalesce(:maxBirthDate, :maxBirthDate) is null or e.birthDate <= :maxBirthDate)" +
        "and (coalesce(:minEmploymentDate, :minEmploymentDate) is null or e.employmentDate >= :minEmploymentDate)" +
        "and (coalesce(:maxEmploymentDate, :maxEmploymentDate) is null or e.employmentDate <= :maxEmploymentDate)" +
        "and (:minSalary is null or e.salary >= :minSalary)" +
        "and (:maxSalary is null or e.salary <= :maxSalary)" +
        "and (:minTeamAverageSalary is null or :minTeamAverageSalary <= " +
                "(select avg(e.salary) from Employee e join e.team et group by et having et.id = t.id)" +
            ")" +
        "and (:maxTeamAverageSalary is null or :maxTeamAverageSalary >= " +
                "(select avg(e.salary) from Employee e join e.team et group by et having et.id = t.id)" +
            ") " +
        "and ((:medExamYear is null or :medExamIsPassed is null) " +
                "or (function('year', m.examDate) = :medExamYear and m.isPassed = :medExamIsPassed))"
    )
    Page<Employee> searchByFilter(
            @Param("sex") Sex sex,
            @Param("name") String name,
            @Param("teamName") String teamName,
            @Param("departmentName") String departmentName,
            @Param("minBirthDate") Date minBirthDate,
            @Param("maxBirthDate") Date maxBirthDate,
            @Param("minEmploymentDate") Date minEmploymentDate,
            @Param("maxEmploymentDate") Date maxEmploymentDate,
            @Param("minSalary") Integer minSalary,
            @Param("maxSalary") Integer maxSalary,
            @Param("minTeamAverageSalary") Double minTeamAverageSalary,
            @Param("maxTeamAverageSalary") Double maxTeamAverageSalary,
            @Param("medExamYear") Integer medExamYear,
            @Param("medExamIsPassed") Boolean medExamIsPassed,
            Pageable pageable
    );

    Page<Employee> getAllByTeamId(Long teamId, Pageable pageable);

}
