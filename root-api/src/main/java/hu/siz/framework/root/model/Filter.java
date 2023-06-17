package hu.siz.framework.root.model;

import java.util.Arrays;
import java.util.List;

/**
 * An object representing a query filter for
 * {@link hu.siz.framework.root.api.MaintenanceAPI#search(List[], long, long, Order[])}
 *
 * @author siz
 */
public record Filter(String fieldName, MatchStyle matchStyle, boolean caseSensitive, String[] values) {

    /**
     * The filter match style constants
     */
    public enum MatchStyle {
        EQUAL, NOTEQUAL, LIKE, IN
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
}
