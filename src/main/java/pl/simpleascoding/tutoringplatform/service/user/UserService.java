package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.*;
import pl.simpleascoding.tutoringplatform.rscp.RscpStatus;


public interface UserService extends UserDetailsService {

    User getUserById(long id);

    User getUserByUsername(String username);

    RscpDTO<?> createUser(CreateUserDTO dto, String rootUrl);

    RscpDTO<?> confirmUserRegistration(String token);

    RscpDTO<?> changeUserPassword(ChangeUserPasswordDTO dto, String username, String rootUrl);

    RscpDTO<?> confirmChangeUserPassword(String token);

    RscpDTO<UserDTO> modifyUser(ModifyUserDTO dto, String username);

    boolean checkUserExists(long id);
}
