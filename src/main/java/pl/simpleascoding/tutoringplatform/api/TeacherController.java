package pl.simpleascoding.tutoringplatform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.dto.TeacherDTO;
import pl.simpleascoding.tutoringplatform.service.tutoring.TutoringFacade;

import java.util.List;

@RestController
@RequestMapping("api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TutoringFacade tutoringFacade;

    @PostMapping("/sign-as-teacher")
    public ResponseEntity<String> addTeacherRoleToUser(@RequestBody SignAsTeacherDTO requestDTO) {
        tutoringFacade.addTeacherRoleToUser(requestDTO);

        return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TeacherDTO>> findAllTeachers() {
        return new ResponseEntity<>(tutoringFacade.findAllTeachers(), HttpStatus.OK);
    }

}
