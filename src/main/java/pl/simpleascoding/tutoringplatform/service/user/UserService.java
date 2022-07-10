package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.stereotype.Service;

import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.dto.GeneralDTO;

@Service
public
interface UserService {

    GeneralDTO createUser(CreateUserDTO dto);

}
