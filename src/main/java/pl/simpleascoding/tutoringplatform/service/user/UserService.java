package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;

@Service
public
interface UserService {

    String createUser(CreateUserDTO dto, String rootUrl);

    String confirmUserRegistration(String token);

    String changeUserPassword(ChangeUserPasswordDTO dto, User user);

}
