package pl.simpleascoding.tutoringplatform.api.publicResource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.simpleascoding.tutoringplatform.domain.user.User;

@RestController
class TestApi {

    @GetMapping("/testAdmin")
    public String testAdmin() {
        return "If you can see this, you are an ADMIN";
    }

    @GetMapping("/testUser")
    public String testUser(@AuthenticationPrincipal User user) {
        return String.format("Hi %s %s, you are a USER", user.getName(), user.getSurname());
    }
}
