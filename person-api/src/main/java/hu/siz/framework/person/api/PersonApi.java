package hu.siz.framework.person.api;

import hu.siz.framework.person.model.PersonDTO;
import hu.siz.framework.root.api.MaintenanceAPI;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@Tag(name = "Person APIs")
public interface PersonApi extends MaintenanceAPI<PersonDTO, UUID> {
}
