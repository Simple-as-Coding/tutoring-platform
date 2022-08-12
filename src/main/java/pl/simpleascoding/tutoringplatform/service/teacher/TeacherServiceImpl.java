package pl.simpleascoding.tutoringplatform.service.teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.domain.user.Role;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.dto.UserDTO;
import pl.simpleascoding.tutoringplatform.exception.UserIsAlreadyATeacherException;
import pl.simpleascoding.tutoringplatform.repository.RoleRepository;
import pl.simpleascoding.tutoringplatform.service.user.UserModelMapper;
import pl.simpleascoding.tutoringplatform.service.user.UserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final RoleRepository roleRepository;
    private final UserService userService;
    private final UserModelMapper userModelMapper;

    @Override
    @Transactional
    public void addTeacherRoleToUser(SignAsTeacherDTO requestDTO) {
        User user = userService.getUserByUsername(requestDTO.username());
        Role roleTeacher = createTeacherRoleEntity();
        isUserAlreadyTeacher(user, roleTeacher);
        addRoleToEntity(user, roleTeacher);
    }

    @Override
    public List<UserDTO> findAllTeachers() {
        List<UserDTO> teacherDTOList = new ArrayList<>();
        completeTeacherDtoListWithDtoObjects(teacherDTOList);

        return teacherDTOList;
    }

    private void isUserAlreadyTeacher(User user, Role roleTeacher) {
        if (user.getRoles().contains(roleTeacher)) {
            throw new UserIsAlreadyATeacherException();
        }
    }

    private void addRoleToEntity(User user, Role roleTeacher) {
        user.getRoles().add(roleTeacher);
    }

    private void completeTeacherDtoListWithDtoObjects(List<UserDTO> teacherDTOList) {
        for (Role teacherRole : roleRepository.findRolesByRoleType(RoleType.TEACHER)) {
            User teacher = teacherRole.getUser();
            teacherDTOList.add(userModelMapper.mapUserEntityToUserDTO(teacher));
        }
    }

    private Role createTeacherRoleEntity() {
        return new Role(RoleType.TEACHER);
    }
}
