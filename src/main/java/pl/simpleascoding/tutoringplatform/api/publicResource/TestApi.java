package pl.simpleascoding.tutoringplatform.api.publicResource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class TestApi {

    @GetMapping("/testAdmin")
    public String testAdmin() {
        return "If you can see this, you are an ADMIN";
    }

    @GetMapping("/testUser")
    public String testUser() {
        return "If you can see this, you are a USER";
    }
}
