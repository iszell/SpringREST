package hu.siz.framework.person.mapping;

import hu.siz.framework.core.mapper.EntityMapper;
import hu.siz.framework.person.entity.Person;
import hu.siz.framework.person.model.PersonDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper extends EntityMapper<Person, PersonDTO> {

    PersonDTO toDTO(Person person);

    Person fromDTO(PersonDTO personDTO);
}
