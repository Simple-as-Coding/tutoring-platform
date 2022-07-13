package pl.simpleascoding.tutoringplatform.api.publicResource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.service.user.UserFacade;

import javax.servlet.http.HttpServletRequest;

import java.security.Principal;

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

    @PostMapping("/change-password")
    ResponseEntity<String> changeUserPassword(@RequestBody ChangeUserPasswordDTO dto, @AuthenticationPrincipal User loggedInUser) {

        return new ResponseEntity<>(userFacade.changeUserPassword(dto, loggedInUser), HttpStatus.OK);

    }

}
