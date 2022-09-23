package pl.simpleascoding.tutoringplatform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.RscpDTO;
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

        RscpDTO<Page<UserDTO>> rscpDTO = teacherService.findAllTeachers(pageable);
        Page<UserDTO> body = rscpDTO.body();
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(body, headers, httpStatus);
    }

    @PostMapping("/sign-as-teacher")
    public ResponseEntity<?> addTeacherRoleToUser(@RequestBody SignAsTeacherDTO requestDTO) {
        RscpDTO<?> rscpDTO = teacherService.addTeacherRoleToUser(requestDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(headers, httpStatus);
    }

}
