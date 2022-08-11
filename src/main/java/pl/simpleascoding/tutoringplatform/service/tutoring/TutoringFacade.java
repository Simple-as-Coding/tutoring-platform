package pl.simpleascoding.tutoringplatform.service.tutoring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.dto.TeacherDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TutoringFacade {

    private final TutoringService tutoringService;

    public void addTeacherRoleToUser(SignAsTeacherDTO requestDTO) {
        tutoringService.addTeacherRoleToUser(requestDTO);
    }

    public List<TeacherDTO> findAllTeachers() {
        return tutoringService.findAllTeachers();
    }

}
