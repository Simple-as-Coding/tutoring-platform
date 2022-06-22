package pl.simpleascoding.tutoringplatform.api.publicResource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {

    @GetMapping("/testapi")
    public String testApi() {
        return "if you can see this message, application is working";
    }
}
