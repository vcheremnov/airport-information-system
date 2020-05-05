package airport.specifications;

import airport.filters.Range;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public class PredicateFactory {

    public static Predicate identicallyTrue(CriteriaBuilder cb) {
        return cb.isTrue(cb.literal(true));
    }

    public static <T extends Comparable<T>,
                   X extends T,
                   Y extends T> Predicate nullableBetween(
            Range<X> range,
            Expression<Y> expression,
            CriteriaBuilder cb
    ) {

        if (range == null) {
            return identicallyTrue(cb);
        }

        if (range.getMinValue() != null && range.getMaxValue() != null) {
            return cb.between(expression, range.getMinValue(), range.getMaxValue());
        }

        if (range.getMinValue() != null) {
            return cb.greaterThanOrEqualTo(expression, range.getMinValue());
        }

        if (range.getMaxValue() != null) {
            return cb.lessThanOrEqualTo(expression, range.getMaxValue());
        }

        return identicallyTrue(cb);

    }

    public static <T> Predicate nullableEquals(
            T value,
            Expression<T> expression,
            CriteriaBuilder cb
    ) {

        if (value == null) {
            return identicallyTrue(cb);
        }

        return cb.equal(expression, value);

    }
}
