package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;

import javax.transaction.Transactional;

@Service
interface UserService {

    String createUser(CreateUserDTO dto);

    @Transactional
    String changeUserPassword(ChangeUserPasswordDTO dto, String username);

}
