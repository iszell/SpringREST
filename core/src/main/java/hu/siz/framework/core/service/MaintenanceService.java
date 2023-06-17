package hu.siz.framework.core.service;

import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.IdentifierWrapper;
import hu.siz.framework.root.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Backend implementation for a {@link hu.siz.framework.root.api.MaintenanceAPI} to be used with
 * {@link hu.siz.framework.core.controller.AbstractMaintenanceController}
 *
 * @param <I> the identifier type of the maintained object (DTO)
 * @param <T> the actual object type to be maintained
 * @author siz
 * @see hu.siz.framework.root.api.MaintenanceAPI
 * @see hu.siz.framework.core.controller.AbstractMaintenanceController
 */
public interface MaintenanceService<T, I> {
    IdentifierWrapper<I> create(T data);

    Page<T> search(List<Filter>[] filter, long page, long size, Order[] order);
}
