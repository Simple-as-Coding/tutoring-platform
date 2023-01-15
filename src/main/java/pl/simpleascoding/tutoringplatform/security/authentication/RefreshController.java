package pl.simpleascoding.tutoringplatform.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.simpleascoding.tutoringplatform.security.SecurityService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static pl.simpleascoding.tutoringplatform.security.SecurityFinals.AUTHORIZATION;

@RestController
@RequestMapping("api/v1/refresh")
@RequiredArgsConstructor
class RefreshController {

    private final SecurityService service;

    @PostMapping
    ResponseEntity<Map<String, String>> refresh(HttpServletRequest request) {
        return new ResponseEntity<>(service.refresh(request.getHeader(AUTHORIZATION), request.getRequestURL().toString()), HttpStatus.OK);
    }

}
