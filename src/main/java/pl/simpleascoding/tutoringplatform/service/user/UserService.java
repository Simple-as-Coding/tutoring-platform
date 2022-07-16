package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;

import java.util.Optional;

@Service
public
interface UserService {

    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username);

    String createUser(CreateUserDTO dto, String rootUrl);

    String confirmUserRegistration(String token);

    String changeUserPassword(ChangeUserPasswordDTO dto, String username);

    boolean checkUserExists(Long id);

}
