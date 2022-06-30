package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;

@Service
interface UserService {

    String createUser(CreateUserDTO dto, String rootUrl);

    String confirmUserRegistration(String token);

}
