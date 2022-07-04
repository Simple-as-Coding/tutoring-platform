package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;

@Service
public
interface UserService {

    String createUser(CreateUserDTO dto);

}
