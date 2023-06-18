package hu.siz.framework.person.service;

import hu.siz.framework.core.service.AbstractJpaMaintenanceService;
import hu.siz.framework.person.entity.Person;
import hu.siz.framework.person.mapping.PersonMapper;
import hu.siz.framework.person.model.PersonDTO;
import hu.siz.framework.person.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public class PersonService extends AbstractJpaMaintenanceService<PersonDTO, UUID, Person> {
    private final PersonRepository repository;
    private final PersonMapper mapper;
}
