package hu.siz.framework.core.repository.criteria;

import hu.siz.framework.root.model.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
        var field = root.get(f.fieldName());
        if (field != null) {
            var javaType = field.getJavaType();
            if (String.class.isAssignableFrom(javaType)) {
                return StringPredicateBuilder.build(root, criteriaBuilder, f);
            }
        }
        return null;
    }
}
