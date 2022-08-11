package pl.simpleascoding.tutoringplatform.service.tutoring;

import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.dto.TeacherDTO;

import java.util.List;

@Service
interface TutoringService {
    void addTeacherRoleToUser(SignAsTeacherDTO requestDTO);

    List<TeacherDTO> findAllTeachers();
}
