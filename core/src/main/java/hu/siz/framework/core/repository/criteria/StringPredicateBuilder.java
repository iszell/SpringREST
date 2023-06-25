package hu.siz.framework.core.repository.criteria;

import hu.siz.framework.root.model.Filter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class StringPredicateBuilder {
    public Predicate build(Root<?> root, CriteriaBuilder criteriaBuilder, Filter f) {
        Expression<String> field = root.get(f.fieldName());
        Object[] values;
        if (f.caseSensitive()) {
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
            case LIKE -> criteriaBuilder.like(field, (String) values[0]);
            case CONTAINS -> criteriaBuilder.like(field, '%' + (String) values[0] + '%');
            case IN -> field.in(values);
            default -> null;
        };
    }
}
