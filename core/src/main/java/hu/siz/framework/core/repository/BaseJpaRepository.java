package hu.siz.framework.core.repository;

import hu.siz.framework.root.model.Filter;
import hu.siz.framework.root.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Arrays;
import java.util.List;

@NoRepositoryBean
public interface BaseJpaRepository<T, I> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
    default Page<T> find(List<Filter>[] filter, int page, int size, Order[] order) {
        PageRequest pageRequest;
        if (order != null) {
            pageRequest = PageRequest.of(page, size,
                    Sort.by(Arrays.stream(order)
                            .map(o -> o.descending() ? Sort.Order.desc(o.field()) : Sort.Order.asc(o.field()))
                            .toList()));
        } else {
            pageRequest = PageRequest.of(page, size);
        }
        if (filter != null) {
            return findAll(new QuerySpecification<>(filter, page, size, order), pageRequest);
        } else {
            return findAll(pageRequest);
        }
    }
}
