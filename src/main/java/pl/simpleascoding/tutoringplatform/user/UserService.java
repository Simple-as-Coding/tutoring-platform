package pl.simpleascoding.tutoringplatform.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.user.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.user.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.user.dto.ModifyUserDTO;
import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;

public interface UserService extends UserDetailsService {

    User getUserById(long id);

    User getUserByUsername(String username);

    RscpDTO<UserDTO> createUser(CreateUserDTO dto, String rootUrl);

    RscpDTO<?> confirmUserRegistration(String token);

    RscpDTO<?> changeUserPassword(ChangeUserPasswordDTO dto, String username, String rootUrl);

    RscpDTO<?> confirmChangeUserPassword(String token);

    RscpDTO<UserDTO> modifyUser(ModifyUserDTO dto, String username);

    boolean checkUserExists(long id);
}
