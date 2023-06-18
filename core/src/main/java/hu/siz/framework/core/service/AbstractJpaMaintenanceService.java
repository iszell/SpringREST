package hu.siz.framework.core.service;

import hu.siz.framework.core.entity.BaseEntity;
import hu.siz.framework.core.mapper.EntityMapper;
import hu.siz.framework.core.repository.BaseJpaRepository;
import hu.siz.framework.root.exception.ObjectNotFoundException;
import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.IdentifierWrapper;
import hu.siz.framework.root.model.Order;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Transactional
public abstract class AbstractJpaMaintenanceService<D, I extends Serializable, E extends BaseEntity<I>>
        implements MaintenanceService<D, I> {

    private Class<E> entityClass;

    @Override
    public IdentifierWrapper<I> create(D data) {
        return new IdentifierWrapper<>(getRepository().saveAndFlush(getMapper().fromDTO(data)).getId());
    }

    @Override
    public D get(I id) {
        return getMapper()
                .toDTO(
                        getRepository()
                                .findById(id)
                                .orElseThrow(() -> new ObjectNotFoundException(getEntityClass(), id)));
    }

    @Override
    public Page<Pair<I, D>> search(List<Filter>[] filter, int page, int size, Order[] order) {
        getEntityClass();
        return getRepository()
                .find(filter, page, size, order)
                .map(e -> Pair.of(e.getId(), getMapper().toDTO(e)));
    }

    protected abstract BaseJpaRepository<E, I> getRepository();

    protected abstract EntityMapper<E, D> getMapper();

    @SneakyThrows
    protected Class<E> getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<E>)
                    this.getClass()
                            .getClassLoader()
                            .loadClass(
                                    ((ParameterizedType)
                                            this.getClass()
                                                    .getGenericSuperclass())
                                            .getActualTypeArguments()[2]
                                            .getTypeName());
        }
        return this.entityClass;
    }
}
