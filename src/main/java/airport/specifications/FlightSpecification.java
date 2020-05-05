package airport.specifications;

import airport.entities.*;
import airport.entities.types.FlightType;
import airport.filters.FlightFilter;
import airport.filters.Range;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FlightSpecification implements Specification<Flight> {

    private final FlightFilter filter;

    public FlightSpecification(FlightFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(
            Root<Flight> root,
            CriteriaQuery<?> cq,
            CriteriaBuilder cb
    ) {

        Path<FlightType> flightType = root.get(Flight_.flightType);
        Path<Serializable> airplaneId = root.get(Flight_.airplane).get(Airplane_.id);
        Path<Serializable> airplaneTypeId = root.get(Flight_.airplane)
                .get(Airplane_.airplaneType)
                .get(AirplaneType_.id);
        Path<Serializable> cityId = root.get(Flight_.city).get(City_.id);
        Path<Boolean> isCancelled = root.get(Flight_.isCancelled);
        Path<Timestamp> flightTime = root.get(Flight_.flightTime);
        Path<String> delayReason = root.get(Flight_.flightDelay).get(FlightDelay_.delayReason);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(
            PredicateFactory.nullableEquals(filter.getFlightType(), flightType, cb)
        );

        predicates.add(
                PredicateFactory.nullableEquals(filter.getAirplaneId(), airplaneId, cb)
        );

        predicates.add(
                PredicateFactory.nullableEquals(filter.getAirplaneTypeId(), airplaneTypeId, cb)
        );

        predicates.add(
                PredicateFactory.nullableEquals(filter.getCityId(), cityId, cb)
        );

        predicates.add(
                PredicateFactory.nullableEquals(filter.getIsCancelled(), isCancelled, cb)
        );

        Range<Timestamp> timeRange = new Range<>();
        timeRange.setMinValue(
                filter.getMinFlightDate() == null ? null : new Timestamp(filter.getMinFlightDate().getTime())
        );
        timeRange.setMaxValue(
                filter.getMaxFlightDate() == null ? null : new Timestamp(filter.getMaxFlightDate().getTime())
        );

        predicates.add(
            PredicateFactory.nullableBetween(timeRange, flightTime, cb)
        );

        if (filter.getIsDelayed() != null) {
            Subquery<FlightDelay> subQuery = cq.subquery(FlightDelay.class);
            Root<FlightDelay> subRoot = subQuery.from(FlightDelay.class);
            Predicate idEqualityPredicate = cb.equal(
                root.get(Flight_.id), subRoot.get(FlightDelay_.id)
            );
            subQuery.select(subRoot).where(idEqualityPredicate);

            Predicate delayExistsPredicate = cb.exists(subQuery);
            predicates.add(filter.getIsDelayed() ? delayExistsPredicate : cb.not(delayExistsPredicate));

            predicates.add(
                PredicateFactory.nullableEquals(filter.getDelayReason(), delayReason, cb)
            );
        }

        cq.distinct(true);
        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
