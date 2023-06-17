package hu.siz.framework.core.repository;

import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.Order;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class QuerySpecification<T> implements Specification<T> {
    private List<Filter>[] filter;
    private long page;
    private long size;
    private Order[] order;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        log.trace("Creating predicate");
        return criteriaBuilder.or(
                Arrays.stream(filter)
                        .map(f -> toAndPredicates(root, criteriaBuilder, f))
                        .filter(p -> p.length > 0)
                        .map(criteriaBuilder::and)
                        .filter(Objects::nonNull)
                        .toList()
                        .toArray(new Predicate[0])
        );
    }

    private Predicate[] toAndPredicates(Root<T> root, CriteriaBuilder criteriaBuilder, List<Filter> filters) {
        return filters.stream()
                .map(f -> toActualPredicate(root, criteriaBuilder, f))
                .filter(Objects::nonNull)
                .toList()
                .toArray(new Predicate[0]);
    }

    private Predicate toActualPredicate(Root<T> root, CriteriaBuilder criteriaBuilder, Filter f) {
        Expression<?> field = root.get(f.fieldName());
        Object[] values;
        if (f.caseSensitive() || !isString(field)) {
            values = f.values();
        } else {
            values = Arrays.stream(f.values())
                    .map(String::toUpperCase)
                    .toList()
                    .toArray(new String[0]);
            field = criteriaBuilder.upper(root.get(f.fieldName()));
        }
        return switch (f.matchStyle()) {
            case EQUAL -> criteriaBuilder.equal(field, (values[0]));
            case NOTEQUAL -> criteriaBuilder.not(criteriaBuilder.equal(field, (values[0])));
            case LIKE -> isString(field) ? criteriaBuilder.like((Expression<String>) field, (String) values[0]) : null;
            case IN -> field.in(values);
        };
    }

    private static boolean isString(Expression<?> field) {
        return String.class.isAssignableFrom(field.getJavaType());
    }
}
