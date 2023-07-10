package hu.siz.framework.core.controller;

import hu.siz.framework.core.service.MaintenanceService;
import hu.siz.framework.root.api.MaintenanceAPI;
import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.IdentifierWrapper;
import hu.siz.framework.root.model.Order;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
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
public abstract class AbstractMaintenanceController<T extends RepresentationModel<T>, I> implements MaintenanceAPI<T, I> {

    protected final Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public IdentifierWrapper<I> create(@Valid T data) {
        log.debug("Create: {}", data);
        var identifierWrapper = getMaintenanceService().create(data);
        return identifierWrapper
                .add(WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(this.getClass()).get(identifierWrapper.getId()))
                        .withSelfRel());
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
    public void update(I id, T dto) {
        log.debug("Update: {} with {}", id, dto);
        getMaintenanceService().update(id, dto);
    }

    @Override
    public PagedModel<EntityModel<T>> search(List<Filter>[] filter, int page, int size, Order[] order) {
        log.debug("Search: {} {} {} {}", filter, page, size, order);
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

    protected abstract PagedResourcesAssembler<T> getPagedResourcesAssembler();

    protected T addLinks(T type, I id) {
        return type.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(this.getClass()).get(id))
                        .withSelfRel());
    }
}
