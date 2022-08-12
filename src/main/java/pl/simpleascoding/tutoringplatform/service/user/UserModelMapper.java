package pl.simpleascoding.tutoringplatform.service.user;

import org.mapstruct.Mapper;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserModelMapper {
    UserDTO mapUserEntityToUserDTO(User user);
}
