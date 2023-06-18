package hu.siz.framework.user.controller;

import hu.siz.framework.core.controller.AbstractMaintenanceController;
import hu.siz.framework.user.api.UserApi;
import hu.siz.framework.user.model.UserDTO;
import hu.siz.framework.user.service.UserService;
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
@RequestMapping("/api/v1/user")
public class UserController extends AbstractMaintenanceController<UserDTO, UUID> implements UserApi {
    private final UserService maintenanceService;
    private final PagedResourcesAssembler pagedResourcesAssembler;
}
