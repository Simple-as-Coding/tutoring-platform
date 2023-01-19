package pl.simpleascoding.tutoringplatform.user;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.user.dto.SignAsTeacherDTO;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;
import pl.simpleascoding.tutoringplatform.user.exception.UserIsAlreadyATeacherException;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpStatus;

import javax.transaction.Transactional;

@Service
@Primary
@RequiredArgsConstructor
class TeacherServiceImpl implements TeacherService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserModelMapper userModelMapper;

    @Override
    @Transactional
    public RscpDTO<?> addTeacherRoleToUser(SignAsTeacherDTO requestDTO) {
        User user = userService.getUserByUsername(requestDTO.username());
        if (!isUserAlreadyTeacher(user)) {
            addRoleToEntity(user, RoleType.TEACHER);
            return new RscpDTO<>(RscpStatus.OK, "Teacher role added to user.", null);
        } else {
            throw new UserIsAlreadyATeacherException();
        }
    }

    @Override
    public RscpDTO<Page<UserDTO>> findAllTeachers(Pageable pageable) {
        return createTeacherDtoPage(pageable);
    }

    private boolean isUserAlreadyTeacher(User user) {
        return user.getRoles().contains(RoleType.TEACHER);
    }

    private void addRoleToEntity(User user, RoleType roleType) {
        user.getRoles().add(roleType);
    }

    private RscpDTO<Page<UserDTO>> createTeacherDtoPage(Pageable pageable) {
        Page<UserDTO> userPage = userRepository.findUsersByRolesContaining(RoleType.TEACHER, pageable)
                .map(userModelMapper::mapUserEntityToUserDTO);

        return new RscpDTO<>(RscpStatus.OK, "Page returned.", userPage);
    }
}
