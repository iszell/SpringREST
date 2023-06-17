package hu.siz.framework.user.mapping;

import hu.siz.framework.core.mapper.EntityMapper;
import hu.siz.framework.user.entity.User;
import hu.siz.framework.user.model.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDTO> {

    UserDTO toDTO(User user);

    User fromDTO(UserDTO userDTO);
}
