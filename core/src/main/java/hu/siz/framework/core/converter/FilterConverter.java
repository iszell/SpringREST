package hu.siz.framework.core.converter;

import hu.siz.framework.root.model.Filter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Convert URL parameter to a list of filters
 *
 * @author siz
 */
@Slf4j
@Component
public class FilterConverter implements Converter<String, List<Filter>> {

    private static final String FILTER_SEPARATOR = ";";
    private static final String VALUE_SEPARATOR = "=";
    private static final String MATCH_STYLE_SEPARATOR = ".";
    private static final String VALUE_LIST_SEPARATOR = ",";
    public static final String CASE_SENSITIVE_MARKER = "*";

    @Override
    public List<Filter> convert(@NonNull String source) {
        if (!StringUtils.hasLength(source)) {
            return new ArrayList<>();
        }
        log.trace("Processing filter list from source {}", source);
        return Arrays.stream(source.split(FILTER_SEPARATOR))
                .filter(s -> s.contains(VALUE_SEPARATOR))
                .map(FilterConverter::stringToFilter)
                .toList();
    }

    private static Filter stringToFilter(String source) {
        log.trace("Processing filter from source {}", source);
        var valueSeparatorIndex = source.indexOf(VALUE_SEPARATOR);
        var fieldName = source.substring(0, valueSeparatorIndex);
        var valueExpression = source.substring(valueSeparatorIndex + 1);
        log.trace("Processing filter for field {} and value {}", fieldName, valueExpression);
        var isCaseSensitive = valueExpression.startsWith(CASE_SENSITIVE_MARKER);
        if (isCaseSensitive) {
            valueExpression = valueExpression.substring(1);
        }
        Optional<Filter.MatchStyle> matchStyle = Optional.empty();
        var matchStyleSeparatorIndex = valueExpression.indexOf(MATCH_STYLE_SEPARATOR);
        if (matchStyleSeparatorIndex >= 0) {
            matchStyle = extractMatchStyle(valueExpression.substring(0, matchStyleSeparatorIndex));
            if (matchStyle.isPresent()) {
                valueExpression = valueExpression.substring(matchStyleSeparatorIndex + 1);
            }
        }
        return new Filter(fieldName,
                matchStyle.orElse(Filter.MatchStyle.EQUAL),
                isCaseSensitive,
                valueExpression.split(VALUE_LIST_SEPARATOR));
    }

    private static Optional<Filter.MatchStyle> extractMatchStyle(String from) {
        try {
            return Optional.of(Filter.MatchStyle.valueOf(from.toUpperCase()));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid filter match style {}", from);
            return Optional.empty();
        }
    }
}
