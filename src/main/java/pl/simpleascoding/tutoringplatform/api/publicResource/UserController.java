package pl.simpleascoding.tutoringplatform.api.publicResource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.simpleascoding.tutoringplatform.common.GeneralStatus;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.dto.GeneralDTO;
import pl.simpleascoding.tutoringplatform.service.user.UserFacade;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserFacade userFacade;

    @PostMapping
    ResponseEntity<HttpHeaders> createUser(@RequestBody CreateUserDTO dto) {
        GeneralDTO createUserDTO = userFacade.createUser(dto); // throw UsernameTakenException
        MultiValueMap<String, String> headers = new HttpHeaders();
            GeneralStatus generalStatus = createUserDTO.status(); // OK(200, GeneralSeries.SUCCESSFUL, "OK"),
            headers.add("status", generalStatus.name());
            headers.add("description", createUserDTO.description());

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

}
