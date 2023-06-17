package hu.siz.framework.user.repository;

import hu.siz.framework.core.repository.BaseJpaRepository;
import hu.siz.framework.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends BaseJpaRepository<User, UUID> {
}
