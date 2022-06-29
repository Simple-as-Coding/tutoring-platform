package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;

@Service
interface UserService {

    String createUser(CreateUserDTO dto);
    String changeUserPassword(ChangeUserPasswordDTO dto);

}
