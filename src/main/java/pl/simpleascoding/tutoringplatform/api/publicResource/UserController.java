package pl.simpleascoding.tutoringplatform.api.publicResource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.service.user.UserFacade;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserFacade userFacade;

    @PostMapping
    ResponseEntity<String> createUser(@RequestBody CreateUserDTO dto, HttpServletRequest request) {
        return new ResponseEntity<>(userFacade.createUser(dto, request.getRequestURL().toString()), HttpStatus.OK);
    }

    @GetMapping("/confirm-registration")
    ResponseEntity<String> confirmRegistration(@RequestParam String tokenValue) {
        return new ResponseEntity<>(userFacade.confirmUserRegistration(tokenValue), HttpStatus.OK);
    }

}
