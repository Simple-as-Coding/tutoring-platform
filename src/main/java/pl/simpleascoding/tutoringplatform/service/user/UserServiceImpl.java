package pl.simpleascoding.tutoringplatform.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.simpleascoding.tutoringplatform.domain.user.Role;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.exception.UsernameTakenException;
import pl.simpleascoding.tutoringplatform.repository.UserRepository;

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String createUser(CreateUserDTO dto) {
        userRepository.findByUsername(dto.username()).ifPresent(user -> {
            throw new UsernameTakenException(dto.username());
        });
        User user = new User(dto.username(), dto.password(), dto.name(), dto.surname());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(new Role(RoleType.USER));
        userRepository.save(user);
        return "SUCCESS";
    }

    @Override
    @Transactional
    public String changeUserPassword(ChangeUserPasswordDTO dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (isConditionToChangePasswordMet(dto, user)) {

            user.setPassword(passwordEncoder.encode(dto.newPassword()));

            return HttpStatus.OK.getReasonPhrase();

        } else {

            return HttpStatus.UNAUTHORIZED.getReasonPhrase();

        }

    }

    private boolean isConditionToChangePasswordMet(ChangeUserPasswordDTO dto, User user) {

        return passwordEncoder.matches(dto.oldPassword(), user.getPassword()) & dto.newPasswordReturn().equals(dto.newPassword());

    }

}
