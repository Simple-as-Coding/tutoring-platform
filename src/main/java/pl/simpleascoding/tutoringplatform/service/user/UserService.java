package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;

@Service
interface UserService {

    String createUser(CreateUserDTO dto);

    ResponseEntity<String> changeUserPassword(ChangeUserPasswordDTO dto);

}
