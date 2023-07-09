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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Transactional
public abstract class AbstractJpaMaintenanceService<D, I extends Serializable, E extends BaseEntity<I>>
        implements MaintenanceService<D, I> {

    private Class<D> dtoClass;
    private Class<E> entityClass;
    @Autowired
    private FieldRepositoryService fieldRepositoryService;

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
    public void update(I id, D data) {
        E entity = getMapper().fromDTO(data);
        entity.setId(id);
        getRepository().saveAndFlush(entity);
    }

    @Override
    public Page<Pair<I, D>> search(List<Filter>[] filter, int page, int size, Order[] order) {
        List<Filter>[] mappedFilters = filter == null ? null :
                Arrays.stream(filter)
                        .map(this::convertFilterList)
                        .toList().toArray(new List[0]);
        return getRepository()
                .find(mappedFilters, page, size, order)
                .map(e -> Pair.of(e.getId(), getMapper().toDTO(e)));
    }

    private List<Filter> convertFilterList(List<Filter> filters) {
        return Objects.requireNonNull(filters)
                .stream()
                .map(f -> new Filter(
                        fieldRepositoryService.getEntityPathFor(getDtoClass(), f.fieldName()),
                        f.matchStyle(),
                        f.caseSensitive(),
                        f.values()))
                .toList();
    }

    protected abstract BaseJpaRepository<E, I> getRepository();

    protected abstract EntityMapper<E, D> getMapper();

    @SneakyThrows
    protected Class<D> getDtoClass() {
        if (dtoClass == null) {
            dtoClass = (Class<D>)
                    this.getClass()
                            .getClassLoader()
                            .loadClass(
                                    ((ParameterizedType)
                                            this.getClass()
                                                    .getGenericSuperclass())
                                            .getActualTypeArguments()[0]
                                            .getTypeName());
        }
        return this.dtoClass;
    }

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
