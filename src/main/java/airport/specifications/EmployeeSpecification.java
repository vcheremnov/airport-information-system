package airport.specifications;

import airport.entities.*;
import airport.entities.types.Sex;
import airport.filters.EmployeeFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification implements Specification<Employee> {

    private final EmployeeFilter filter;

    public EmployeeSpecification(EmployeeFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(
            Root<Employee> root,
            CriteriaQuery<?> cq,
            CriteriaBuilder cb
    ) {

        Path<Sex> sex = root.get(Employee_.sex);
        Path<Date> birthDate = root.get(Employee_.birthDate);
        Path<Date> employmentDate = root.get(Employee_.employmentDate);
        Path<Integer> salary = root.get(Employee_.salary);
        Path<Serializable> departmentId = root.get(Employee_.team)
                .get(Team_.department)
                .get(Department_.id);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
                PredicateFactory.nullableEquals(filter.getSex(), sex, cb)
        );

        predicates.add(
                PredicateFactory.nullableEquals(filter.getDepartmentId(), departmentId, cb)
        );

        predicates.add(
                PredicateFactory.nullableBetween(
                        filter.getBirthDateRange(), birthDate, cb)
        );

        predicates.add(
                PredicateFactory.nullableBetween(
                        filter.getEmploymentDateRange(), employmentDate, cb
                )
        );

        predicates.add(
                PredicateFactory.nullableBetween(filter.getSalaryRange(), salary, cb)
        );

        cq.distinct(true);
        return cb.and(predicates.toArray(Predicate[]::new));

    }
}
