package pl.simpleascoding.tutoringplatform.user.teacher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.user.teacher.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;

public interface TeacherService {
    RscpDTO<?> addTeacherRoleToUser(SignAsTeacherDTO requestDTO);

    RscpDTO<Page<UserDTO>> findAllTeachers(Pageable pageable);
}
