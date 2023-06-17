package hu.siz.framework.core.service;

import hu.siz.framework.core.entity.BaseEntity;
import hu.siz.framework.core.mapper.EntityMapper;
import hu.siz.framework.core.repository.BaseJpaRepository;
import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.IdentifierWrapper;
import hu.siz.framework.root.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class AbstractJpaMaintenanceService<D, I extends Serializable, E extends BaseEntity<I>>
        implements MaintenanceService<D, I> {

    @Override
    public IdentifierWrapper<I> create(D data) {
        return new IdentifierWrapper<>(getRepository().saveAndFlush(getMapper().fromDTO(data)).getId());
    }

    @Override
    public Page<D> search(List<Filter>[] filter, long page, long size, Order[] order) {
        return new PageImpl<>(
                getRepository().find(filter, page, size, order)
                        .map(e -> getMapper().toDTO(e))
                        .toList());
    }

    protected abstract BaseJpaRepository<E, I> getRepository();

    protected abstract EntityMapper<E, D> getMapper();
}
