package pl.simpleascoding.tutoringplatform.service.mediator;

import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.service.user.UserService;

public class CommanderImpl implements Commander {

    UserService userService;


    @Override
    public void registerServices(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createUser(UserService user_service, CreateUserDTO dto) {
        user_service.createUser(dto);
    }
}
