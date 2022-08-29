package pl.simpleascoding.tutoringplatform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.dto.UserDTO;
import pl.simpleascoding.tutoringplatform.service.teacher.TeacherService;

@RestController
@RequestMapping("api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/all")
    public ResponseEntity<Page<UserDTO>> findAllTeachers(Pageable pageable) {
        return new ResponseEntity<>(teacherService.findAllTeachers(pageable), HttpStatus.OK);
    }

    @PostMapping("/sign-as-teacher")
    public ResponseEntity<String> addTeacherRoleToUser(@RequestBody SignAsTeacherDTO requestDTO) {
        teacherService.addTeacherRoleToUser(requestDTO);

        return new ResponseEntity<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK);
    }

}
