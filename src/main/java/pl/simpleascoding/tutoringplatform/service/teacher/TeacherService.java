package pl.simpleascoding.tutoringplatform.service.teacher;

import pl.simpleascoding.tutoringplatform.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.dto.UserDTO;

import java.util.List;

public interface TeacherService {
    void addTeacherRoleToUser(SignAsTeacherDTO requestDTO);

    List<UserDTO> findAllTeachers();
}
