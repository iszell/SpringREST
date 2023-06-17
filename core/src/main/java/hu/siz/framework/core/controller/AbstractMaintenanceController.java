package hu.siz.framework.core.controller;

import hu.siz.framework.core.service.MaintenanceService;
import hu.siz.framework.root.api.MaintenanceAPI;
import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.IdentifierWrapper;
import hu.siz.framework.root.model.Order;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Default implementation for {@link MaintenanceAPI} through a {@link MaintenanceService} implementation
 *
 * @param <I> the identifier type of the maintained object (DTO)
 * @param <T> the actual object type to be maintained
 * @see MaintenanceAPI
 * @see MaintenanceService
 */
@Slf4j
public abstract class AbstractMaintenanceController<T, I> implements MaintenanceAPI<T, I> {
    @Override
    public IdentifierWrapper<I> create(@Valid T data) {
        log.debug("Create: {}", data);
        return getMaintenanceService()
                .create(data);
    }

    @Override
    public Page<T> search(List<Filter>[] filter, long page, long size, Order[] order) {
        log.debug("Search: {} {} {} {}", filter, page, page, order);
        return getMaintenanceService()
                .search(filter, page, size, order);
    }

    /**
     * Return the maintenance service to be used.
     *
     * @return the service
     */
    protected abstract MaintenanceService<T, I> getMaintenanceService();
}
