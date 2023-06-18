package hu.siz.framework.core.mapper;

public interface EntityMapper<E, D> {
    D toDTO(E entity);

    E fromDTO(D dto);
}
