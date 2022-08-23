package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.dto.ModifyUserDTO;
import pl.simpleascoding.tutoringplatform.dto.UserDTO;


public interface UserService extends UserDetailsService {

    User getUserById(long id);

    User getUserByUsername(String username);

    String createUser(CreateUserDTO dto, String rootUrl);

    String confirmUserRegistration(String token);

    String changeUserPassword(ChangeUserPasswordDTO dto, String username);

    String modifyUser(ModifyUserDTO dto, String username);

    boolean checkUserExists(long id);
}
