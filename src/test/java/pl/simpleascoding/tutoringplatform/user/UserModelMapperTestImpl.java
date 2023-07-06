package pl.simpleascoding.tutoringplatform.user;

import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;

public class UserModelMapperTestImpl implements UserModelMapper {
    @Override
    public UserDTO mapUserEntityToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail(), user.getRoles());
    }
}
