package hu.siz.framework.root.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * An object representing a query filter for
 * {@link hu.siz.framework.root.api.MaintenanceAPI#search(List[], int, int, Order[])}
 *
 * @author siz
 */
public record Filter(String fieldName, MatchStyle matchStyle, boolean caseSensitive, String[] values)
        implements Serializable {

    /**
     * The filter match style constants
     */
    public enum MatchStyle {
        EQUAL, NOTEQUAL, LIKE, CONTAINS, IN, LT, LE, GT, GE
    }

    @Override
    public String toString() {
        return "Filter<"
                + fieldName
                + " " + matchStyle
                + " " + (caseSensitive ? "(!)" : "")
                + (values.length == 1 ? values[0] : Arrays.toString(values))
                + ">";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filter filter = (Filter) o;
        return caseSensitive == filter.caseSensitive
                && Objects.equals(fieldName, filter.fieldName)
                && matchStyle == filter.matchStyle
                && Arrays.equals(values, filter.values);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fieldName, matchStyle, caseSensitive);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }
}
