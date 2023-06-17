package hu.siz.framework.core.repository;

import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.stream.Stream;

@NoRepositoryBean
public interface BaseJpaRepository<T, I> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
    default Stream<T> find(List<Filter>[] filter, long page, long size, Order[] order) {
        return findAll(new QuerySpecification<>(filter, page, size, order)).stream();
    }
}
