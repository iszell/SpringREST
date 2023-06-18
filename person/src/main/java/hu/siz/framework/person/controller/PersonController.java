package hu.siz.framework.person.controller;

import hu.siz.framework.core.controller.AbstractMaintenanceController;
import hu.siz.framework.person.api.PersonApi;
import hu.siz.framework.person.model.PersonDTO;
import hu.siz.framework.person.service.PersonService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@Getter(AccessLevel.PROTECTED)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/person")
public class PersonController extends AbstractMaintenanceController<PersonDTO, UUID> implements PersonApi {
    private final PersonService maintenanceService;
    private final PagedResourcesAssembler pagedResourcesAssembler;
}
