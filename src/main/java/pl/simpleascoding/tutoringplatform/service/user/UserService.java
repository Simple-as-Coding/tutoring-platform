package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.*;


public interface UserService extends UserDetailsService {

    User getUserById(long id);

    User getUserByUsername(String username);

    String createUser(CreateUserDTO dto, String rootUrl);

    String confirmUserRegistration(String token);

    String changeUserPassword(ChangeUserPasswordDTO dto, String username);

    RscpDTO<UserDTO> modifyUser(ModifyUserDTO dto, String username);

    boolean checkUserExists(long id);
}
