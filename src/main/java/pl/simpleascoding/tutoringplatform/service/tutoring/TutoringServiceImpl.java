package pl.simpleascoding.tutoringplatform.service.tutoring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.domain.user.Role;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.dto.TeacherDTO;
import pl.simpleascoding.tutoringplatform.exception.UserIsAlreadyATeacherException;
import pl.simpleascoding.tutoringplatform.repository.RoleRepository;
import pl.simpleascoding.tutoringplatform.service.user.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
class TutoringServiceImpl implements TutoringService {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public void addTeacherRoleToUser(SignAsTeacherDTO requestDTO) {
        User user = userService.getUserByUsername(requestDTO.username()).get();
        Role roleTeacher = createTeacherRoleEntity();
        if (user.getRoles().contains(roleTeacher)) {
            throw new UserIsAlreadyATeacherException();
        } else {
            user.getRoles().add(roleTeacher);
        }
    }

    @Override
    public List<TeacherDTO> findAllTeachers() {
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        completeTeacherDtoListWithDtoObjects(teacherDTOList);

        return teacherDTOList;
    }

    private void completeTeacherDtoListWithDtoObjects(List<TeacherDTO> teacherDTOList) {
        for (Role teacherRole : roleRepository.findAllByRoleType(RoleType.TEACHER)) {
            User teacher = teacherRole.getUser();
            teacherDTOList.add(new TeacherDTO(teacher.getName(), teacher.getSurname(), teacher.getEmail()));
        }
    }

    private Role createTeacherRoleEntity() {
        return new Role(RoleType.TEACHER);
    }

}
