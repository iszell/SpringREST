package hu.siz.framework.user.api;

import hu.siz.framework.root.api.MaintenanceAPI;
import hu.siz.framework.user.model.UserDTO;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.UUID;

@Tag(name = "User APIs")
public interface UserApi extends MaintenanceAPI<UserDTO, UUID> {
}
