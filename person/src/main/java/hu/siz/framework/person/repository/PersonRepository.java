package hu.siz.framework.person.repository;

import hu.siz.framework.core.repository.BaseJpaRepository;
import hu.siz.framework.person.entity.Person;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonRepository extends BaseJpaRepository<Person, UUID> {
}
