package pl.simpleascoding.tutoringplatform.api.publicResource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.simpleascoding.tutoringplatform.dto.CredentialsDTO;
import pl.simpleascoding.tutoringplatform.service.security.SecurityService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("api/v1/login")
@RequiredArgsConstructor
public class LoginController {

    private final SecurityService service;

    @PostMapping
    public Map<String, String> login(@RequestBody CredentialsDTO dto, HttpServletRequest request) {
        return service.login(dto, request.getRequestURI());
    }

}
