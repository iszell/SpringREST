package hu.siz.framework.root.model;

import java.util.List;

/**
 * An object representing an order specification for
 * {@link hu.siz.framework.root.api.MaintenanceAPI#search(List[], long, long, Order[])}
 *
 * @param field      the field for ordering
 * @param descending switch to turn on descending ordering
 * @author siz
 */
public record Order(String field, boolean descending) {
}
