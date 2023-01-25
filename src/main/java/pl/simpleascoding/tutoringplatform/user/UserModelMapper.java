package pl.simpleascoding.tutoringplatform.user;

import org.mapstruct.Mapper;
import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserModelMapper {
    UserDTO mapUserEntityToUserDTO(User user);
}
