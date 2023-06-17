package hu.siz.framework.user.service;

import hu.siz.framework.core.service.AbstractJpaMaintenanceService;
import hu.siz.framework.user.entity.User;
import hu.siz.framework.user.mapping.UserMapper;
import hu.siz.framework.user.model.UserDTO;
import hu.siz.framework.user.repository.UserRepository;
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
public class UserService extends AbstractJpaMaintenanceService<UserDTO, UUID, User> {
    private final UserRepository repository;
    private final UserMapper mapper;
}
