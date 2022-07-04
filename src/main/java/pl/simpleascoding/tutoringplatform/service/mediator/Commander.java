package pl.simpleascoding.tutoringplatform.service.mediator;

import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.service.user.UserService;


public interface Commander {

    void registerServices(UserService userService); // todo Add more services

    void createUser(UserService userService, CreateUserDTO dto); // fixme Not sure what type of args here, dto or userService or both

}
