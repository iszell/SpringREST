package hu.siz.framework.person.mapping;

import hu.siz.framework.core.mapper.EntityMapper;
import hu.siz.framework.person.entity.Person;
import hu.siz.framework.person.model.PersonDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PersonMapper extends EntityMapper<Person, PersonDTO> {

    @Mapping(source = "amount", target = "salaryAmount")
    PersonDTO toDTO(Person person);

    @InheritInverseConfiguration
    Person fromDTO(PersonDTO personDTO);
}
