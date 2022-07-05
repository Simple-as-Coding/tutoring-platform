package pl.simpleascoding.tutoringplatform.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public String createUser(CreateUserDTO dto, String rootUrl) {

        return userService.createUser(dto, rootUrl);

    }

    public String confirmUserRegistration(String tokenValue) {

        return userService.confirmUserRegistration(tokenValue);

    }

    public String changeUserPassword(ChangeUserPasswordDTO dto, String username) {

        return userService.changeUserPassword(dto, username);

    }

}
