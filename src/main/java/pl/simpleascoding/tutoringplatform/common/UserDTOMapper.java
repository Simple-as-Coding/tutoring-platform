package pl.simpleascoding.tutoringplatform.common;

import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.UserDTO;

public final class UserDTOMapper {
    private UserDTOMapper() {
    }

    public static UserDTO map(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getName(), user.getSurname(), user.getEmail());
    }
}
