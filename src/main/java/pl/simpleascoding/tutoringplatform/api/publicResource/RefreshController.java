package pl.simpleascoding.tutoringplatform.api.publicResource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.simpleascoding.tutoringplatform.service.security.SecurityService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static pl.simpleascoding.tutoringplatform.common.SecurityFinals.AUTHORIZATION;

@RestController
@RequestMapping("api/v1/refresh")
@RequiredArgsConstructor
public class RefreshController {

    private final SecurityService service;

    @PostMapping
    Map<String, String> refresh(HttpServletRequest request) {
        return service.refresh(request.getHeader(AUTHORIZATION), request.getRequestURI());
    }

}
