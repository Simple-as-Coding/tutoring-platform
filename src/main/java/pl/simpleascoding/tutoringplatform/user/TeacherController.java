package pl.simpleascoding.tutoringplatform.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.user.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;
import pl.simpleascoding.tutoringplatform.util.ControllerUtils;

@RestController
@RequestMapping("api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/all")
    public ResponseEntity<Page<UserDTO>> findAllTeachers(Pageable pageable) {
        RscpDTO<Page<UserDTO>> rscpDTO = teacherService.findAllTeachers(pageable);

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @PostMapping("/sign-as-teacher")
    public ResponseEntity<?> addTeacherRoleToUser(@RequestBody SignAsTeacherDTO requestDTO) {
        RscpDTO<?> rscpDTO = teacherService.addTeacherRoleToUser(requestDTO);

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

}
