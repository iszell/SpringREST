package hu.siz.framework.core.mapper;

public interface EntityMapper<E, D> {
    D toDTO(E user);

    E fromDTO(D userDTO);
}
