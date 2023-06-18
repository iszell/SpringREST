package hu.siz.framework.core.controller;

import hu.siz.framework.core.service.MaintenanceService;
import hu.siz.framework.root.api.MaintenanceAPI;
import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.IdentifierWrapper;
import hu.siz.framework.root.model.Order;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

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
public abstract class AbstractMaintenanceController<T extends RepresentationModel<T>, I> implements MaintenanceAPI<T, I> {
    @Override
    public IdentifierWrapper<I> create(@Valid T data) {
        log.debug("Create: {}", data);
        return getMaintenanceService()
                .create(data);
    }

    @Override
    public T get(I id) {
        log.debug("Get: {}", id);
        return addLinks(
                getMaintenanceService()
                        .get(id)
                , id);
    }

    @Override
    public PagedModel<T> search(List<Filter>[] filter, long page, long size, Order[] order) {
        log.debug("Search: {} {} {} {}", filter, page, page, order);
        return getPagedResourcesAssembler()
                .toModel(
                        getMaintenanceService()
                                .search(filter, page, size, order)
                                .map(pair -> addLinks(pair.getSecond(), pair.getFirst()))
                );
    }

    /**
     * Return the maintenance service to be used.
     *
     * @return the service
     */
    protected abstract MaintenanceService<T, I> getMaintenanceService();

    protected abstract PagedResourcesAssembler getPagedResourcesAssembler();

    protected T addLinks(T type, I id) {
        return type.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(this.getClass()).get(id))
                        .withSelfRel());
    }
}
