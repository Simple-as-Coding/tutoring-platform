package pl.simpleascoding.tutoringplatform.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserServiceImpl userServiceImpl;

    public String createUser(CreateUserDTO dto) {

        return userServiceImpl.createUser(dto);

    }

    public String changeUserPassword(ChangeUserPasswordDTO dto) {

        return userServiceImpl.changeUserPassword(dto);

    }

}
