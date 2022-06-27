package pl.simpleascoding.tutoringplatform.api.publicResource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.service.user.UserFacade;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserFacade userFacade;

    @PostMapping
    ResponseEntity<String> createUser(@RequestBody CreateUserDTO dto) {
        return new ResponseEntity<>(userFacade.createUser(dto), HttpStatus.OK);
    }

}
